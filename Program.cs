namespace proj
{
    using ConsoleTables;
    using OfficeOpenXml;
    using System;
    using System.Collections.Generic;

    class Program
    {

        private static readonly string ProjectPath = @"C:\Users\LEGION\source\repos\proj";
        private static readonly string AutoSavePath = Path.Combine(ProjectPath, "auto_save.csv");
        private static readonly string DefaultExcelPath = Path.Combine(ProjectPath, "employees_data.xlsx");

        static void Main()
        {
            try
            {
                // Initialize Excel license
                ExcelPackage.LicenseContext = LicenseContext.NonCommercial;

                // Ensure project directory exists
                if (!Directory.Exists(ProjectPath))
                {
                    Directory.CreateDirectory(ProjectPath);
                    Console.WriteLine("Created project data directory.");
                }

                Company company = new Company("ECorp");
                InitializeDefaultDepartments(company);

                // Try to load data automatically from project folder
                if (!TryAutoLoadData(company))
                {
                    Console.WriteLine("Starting with empty data.");
                    System.Threading.Thread.Sleep(1000);
                }

                while (true)
                {
                    Console.Clear();
                    DisplayMainMenu();

                    string choice = Console.ReadLine();
                    if (choice == "14") break; // Exit option

                    ProcessMenuChoice(company, choice);

                    Console.WriteLine("\nPress any key to continue...");
                    Console.ReadKey();
                }

                // Save data automatically to project folder on exit
                TryAutoSaveData(company);
                Console.WriteLine("Data saved. Goodbye!");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Fatal error: {ex.Message}");
                Console.ReadKey();
            }
        }

        static bool TryAutoLoadData(Company company)
        {
            try
            {
                // Try CSV first
                if (File.Exists(AutoSavePath))
                {
                    Console.Write("Found saved CSV data. Load it? (Y/N): ");
                    if (Console.ReadLine().Equals("Y", StringComparison.OrdinalIgnoreCase))
                    {
                        Console.WriteLine("Loading data from CSV...");
                        company.ImportFromCSV(AutoSavePath);
                        Console.WriteLine("Data loaded successfully from CSV!");
                        return true;
                    }
                }

                // Then try Excel if CSV not found or not chosen
                if (File.Exists(DefaultExcelPath))
                {
                    Console.Write("Found Excel data. Load it? (Y/N): ");
                    if (Console.ReadLine().Equals("Y", StringComparison.OrdinalIgnoreCase))
                    {
                        Console.WriteLine("Loading data from Excel...");
                        ImportFromExcel(company, DefaultExcelPath);
                        Console.WriteLine("Data loaded successfully from Excel!");
                        return true;
                    }
                }
                return false;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error loading data: {ex.Message}");
                return false;
            }
        }

        static void TryAutoSaveData(Company company)
        {
            try
            {
                Console.WriteLine("\nSaving data...");

                // Save to CSV
                company.ExportToCSV(AutoSavePath);
                Console.WriteLine($"✓ Data saved to CSV: {AutoSavePath}");

                // Save to Excel
                ExportToExcel(company, DefaultExcelPath);
                Console.WriteLine($"✓ Data saved to Excel: {DefaultExcelPath}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error saving data: {ex.Message}");
            }
        }

        static void ImportFromExcel(Company company, string filePath)
        {
            try
            {
                if (!File.Exists(filePath))
                {
                    Console.WriteLine("Error: File not found");
                    return;
                }

                using (ExcelPackage package = new ExcelPackage(new FileInfo(filePath)))
                {
                    ExcelWorksheet worksheet = package.Workbook.Worksheets[0];
                    if (worksheet.Dimension == null)
                    {
                        Console.WriteLine("Error: Empty worksheet");
                        return;
                    }

                    int addedCount = 0;
                    for (int row = 2; row <= worksheet.Dimension.Rows; row++)
                    {
                        try
                        {
                            int id = int.Parse(worksheet.Cells[row, 1].Text);
                            string name = worksheet.Cells[row, 2].Text;
                            int age = int.Parse(worksheet.Cells[row, 3].Text);
                            decimal salary = decimal.Parse(worksheet.Cells[row, 4].Text);
                            string dept = worksheet.Cells[row, 5].Text;
                            DateTime hireDate = DateTime.Parse(worksheet.Cells[row, 6].Text);
                            double rating = double.Parse(worksheet.Cells[row, 7].Text);
                            bool isTerminated = worksheet.Cells[row, 8].Text == "Terminated";

                            // Check if employee already exists
                            if (company.AllEmployees.Any(e => e.ID == id))
                            {
                                Console.WriteLine($"Skipping duplicate employee ID: {id}");
                                continue;
                            }

                            Employee emp = new Employee(id, name, age, salary, dept)
                            {
                                EmploymentDate = hireDate,
                                PerformanceRating = rating,
                                IsTerminated = isTerminated
                            };

                            company.AddEmployee(emp, dept);
                            addedCount++;
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine($"⚠️ Error in row {row}: {ex.Message}");
                        }
                    }
                    Console.WriteLine($"\nSuccessfully imported {addedCount} employees.");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Import failed: {ex.Message}");
            }
        }

       
        static void InitializeDefaultDepartments(Company company)
        {
            company.AddDepartment(new Department("IT", "Mohamed Abdullah"));
            company.AddDepartment(new Department("HR", "Sleem"));
            company.AddDepartment(new Department("Finance", "Ahmed Ali"));
            company.AddDepartment(new Department("Operations", "Samir Mohamed"));
        }

        static void DisplayMainMenu()
        {
            Console.WriteLine("=== Employee Management System ===");
            Console.WriteLine(" 1. Add Employee");
            Console.WriteLine(" 2. View Employees by Department");
            Console.WriteLine(" 3. Promote Employee");
            Console.WriteLine(" 4. Transfer Employee");
            Console.WriteLine(" 5. Terminate Employee");
            Console.WriteLine(" 6. Generate Reports");
            Console.WriteLine(" 7. Search Employees");
            Console.WriteLine(" 8. Add Performance Review");
            Console.WriteLine(" 9. View Employee Performance");
            Console.WriteLine("10. Process Promotions");
            Console.WriteLine("11. Export to CSV");
            Console.WriteLine("12. Import from CSV");
            Console.WriteLine("13. Export to Excel");
            Console.WriteLine("14. Exit");
            Console.Write("\nSelect an option: ");
        }

        static void ProcessMenuChoice(Company company, string choice)
        {
            switch (choice)
            {
                case "1": AddEmployee(company); break;
                case "2": company.GenerateDepartmentReport(); break;
                case "3": PromoteEmployee(company); break;
                case "4": TransferEmployee(company); break;
                case "5": TerminateEmployee(company); break;
                case "6": ShowReports(company); break;
                case "7": SearchEmployees(company); break;
                case "8": AddPerformanceReview(company); break;
                case "9": ViewPerformance(company); break;
                case "10": ProcessPromotions(company); break;
                case "11": ExportToCSV(company); break;
                case "12": ImportFromCSV(company); break;
                case "13": ExportToExcel(company, DefaultExcelPath); break;
                default: Console.WriteLine("Invalid choice. Try again."); break;
            }
        }

        static void ExportToExcel(Company company, string filePath)
        {
            try
            {
                using (var package = new ExcelPackage())
                {
                    var worksheet = package.Workbook.Worksheets.Add("Employees");

                    // Headers with formatting
                    using (var headerCells = worksheet.Cells[1, 1, 1, 8])
                    {
                        headerCells.Style.Font.Bold = true;
                        headerCells.Style.Fill.PatternType = OfficeOpenXml.Style.ExcelFillStyle.Solid;
                        headerCells.Style.Fill.BackgroundColor.SetColor(System.Drawing.Color.LightGray);
                    }

                    worksheet.Cells[1, 1].Value = "ID";
                    worksheet.Cells[1, 2].Value = "Name";
                    worksheet.Cells[1, 3].Value = "Age";
                    worksheet.Cells[1, 4].Value = "Salary";
                    worksheet.Cells[1, 5].Value = "Department";
                    worksheet.Cells[1, 6].Value = "Hire Date";
                    worksheet.Cells[1, 7].Value = "Performance";
                    worksheet.Cells[1, 8].Value = "Status";

                    // Data
                    int row = 2;
                    foreach (Employee emp in company.AllEmployees)
                    {
                        worksheet.Cells[row, 1].Value = emp.ID;
                        worksheet.Cells[row, 2].Value = emp.Name;
                        worksheet.Cells[row, 3].Value = emp.Age;
                        worksheet.Cells[row, 4].Value = emp.Salary;
                        worksheet.Cells[row, 5].Value = emp.Department;
                        worksheet.Cells[row, 6].Value = emp.EmploymentDate;
                        worksheet.Cells[row, 6].Style.Numberformat.Format = "yyyy-mm-dd";
                        worksheet.Cells[row, 7].Value = emp.PerformanceRating;
                        worksheet.Cells[row, 8].Value = emp.IsTerminated ? "Terminated" : "Active";
                        row++;
                    }

                    // Formatting
                    worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();
                    package.SaveAs(new FileInfo(filePath));
                }
                Console.WriteLine($"✓ Excel file saved to: {filePath}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error exporting to Excel: {ex.Message}");
            }
        }

        static void ExportToCSV(Company company)
        {
            try
            {
                string filePath = Path.Combine(ProjectPath, "employees_export.csv");
                company.ExportToCSV(filePath);
                Console.WriteLine($"✓ CSV export saved to: {filePath}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"CSV export failed: {ex.Message}");
            }
        }

        static void ImportFromCSV(Company company)
        {
            try
            {
                string filePath = Path.Combine(ProjectPath, "employees_import.csv");
                if (!File.Exists(filePath))
                {
                    Console.WriteLine("Error: Import file not found");
                    return;
                }
                company.ImportFromCSV(filePath);
                Console.WriteLine($"✓ CSV import from: {filePath}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"CSV import failed: {ex.Message}");
            }
        }


        static void SearchEmployees(Company company)
            {
                Console.Write("\nEnter search term (name or ID): ");
                string term = Console.ReadLine();

                var results = new List<Employee>();
                foreach (var emp in company.AllEmployees)
                {
                    if (emp.Name.Contains(term, StringComparison.OrdinalIgnoreCase) ||
                        emp.ID.ToString() == term)
                    {
                        results.Add(emp);
                    }
                }

                if (results.Count == 0)
                {
                    Console.WriteLine("No matching employees found.");
                    return;
                }

                var table = new ConsoleTable("ID", "Name", "Department", "Salary");
                foreach (var emp in results)
                {
                    table.AddRow(emp.ID, emp.Name, emp.Department, emp.Salary.ToString("C"));
                }
                Console.WriteLine(table.ToString());
            }

            //static void InitializeSampleData(Company company)
            //{
            //    company.AddDepartment(new Department("IT", " Mohamed Abdullah"));
            //    company.AddDepartment(new Department("HR", " Sleeeeeem"));

            //    var emp1 = new Employee(1, "Peter", 30, 60000, "IT");
            //    var emp2 = new Employee(2, "menna", 28, 55000, "HR");
            //    emp1.UpdatePerformance(4.7);
            //    emp2.UpdatePerformance(4.2);

            //    company.AddEmployee(emp1, "IT");
            //    company.AddEmployee(emp2, "HR");
            //}

            static void AddEmployee(Company company)
            {
                try
                {
                    Console.Write("Enter Employee ID: ");
                    int id = int.Parse(Console.ReadLine());

                    Console.Write("Enter Name: ");
                    string name = Console.ReadLine();

                    Console.Write("Enter Age: ");
                    int age = int.Parse(Console.ReadLine());

                    Console.Write("Enter Salary: ");
                    decimal salary = decimal.Parse(Console.ReadLine());

                    Console.Write("Enter Department: ");
                    string dept = Console.ReadLine();

                    var employee = new Employee(id, name, age, salary, dept);
                    company.AddEmployee(employee, dept);
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error: {ex.Message}");
                }
            }
            static void AddPerformanceReview(Company company)
            {
                Console.Write("Enter Employee ID: ");
                int id = int.Parse(Console.ReadLine());

                Employee employee = null;
                foreach (Employee emp in company.AllEmployees)
                {
                    if (emp.ID == id)
                    {
                        employee = emp;
                        break;
                    }
                }

                if (employee == null)
                {
                    Console.WriteLine("Employee not found.");
                    return;
                }

                Console.Write("Enter Rating (0-5): ");
                double rating = double.Parse(Console.ReadLine());

                Console.Write("Enter Comments: ");
                string comments = Console.ReadLine();

                employee.AddPerformanceReview(rating, comments);
            }

            static void ViewPerformance(Company company)
            {
                Console.Write("Enter Employee ID: ");
                int id = int.Parse(Console.ReadLine());
                company.ViewEmployeePerformance(id);
            }

            static void ProcessPromotions(Company company)
            {
                Console.Write("Enter salary increase amount for promotions: ");
                decimal increase = decimal.Parse(Console.ReadLine());
                company.ProcessPromotions(increase);
            }
            static void PromoteEmployee(Company company)
            {
                Console.Write("Enter Employee ID to promote: ");
                int id = int.Parse(Console.ReadLine());

                Employee employee = null;
                foreach (Employee emp in company.AllEmployees)
                {
                    if (emp.ID == id)
                    {
                        employee = emp;
                        break;
                    }
                }
                if (employee == null)
                {
                    Console.WriteLine("Employee not found.");
                    return;
                }

                Console.Write("Enter salary increase amount: ");
                decimal increase = decimal.Parse(Console.ReadLine());
           
                employee.Promote(increase);
            }

        static void TransferEmployee(Company company)
        {
            int id;
            while (true)
            {
                Console.Write("Enter Employee ID to transfer: ");
                string input = Console.ReadLine();

                if (int.TryParse(input, out id))
                    break;

                Console.WriteLine("Invalid input! Please enter a valid numeric Employee ID.");
            }

            Employee employee = null;
            foreach (Employee emp in company.AllEmployees)
            {
                if (emp.ID == id)
                {
                    employee = emp;
                    break;
                }
            }

            if (employee == null)
            {
                Console.WriteLine("Employee not found.");
                return;
            }

            Console.Write("Enter new department: ");
            string newDept = Console.ReadLine()?.Trim();
            Department d;
            foreach (Department dept in company.Departments)
            {
                if (dept.Name == newDept)
                {
                     d = dept;
                    employee.TransferDepartment(d,company);
                }
            }
            while (string.IsNullOrEmpty(newDept))
            {
                Console.WriteLine("Department name cannot be empty. Please enter a valid department:");
                newDept = Console.ReadLine()?.Trim();
            }

            
        }


        static void TerminateEmployee(Company company)
            {
                Console.Write("Enter Employee ID to terminate: ");
                int id = int.Parse(Console.ReadLine());

                Employee employee = null;
                foreach (Employee emp in company.AllEmployees)
                {
                    if (emp.ID == id)
                    {
                        employee = emp;
                        break;
                    }
                }
                if (employee == null)
                {
                    Console.WriteLine("Employee not found.");
                    return;
                }

                employee.Terminate();
            }

            static void ShowReports(Company company)
            {
                Console.WriteLine("\n=== Reports ===");
                company.GenerateDepartmentReport();
                company.GenerateTopPerformersReport();
                company.GenerateSalaryReport();
                //ExcelExporter.ExportSalaryReport(company, "SalaryReport.xlsx");
            }

        }
    }
