using ConsoleTables;
using OfficeOpenXml;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proj {
 public class Company
    {
        public string Name { get; set; }
        public  List<Department> Departments { get; private set; }
        public List<Employee> AllEmployees { get; private set; }

        public Company(string name)
        {
            Name = name;
            Departments = new List<Department>();
            AllEmployees = new List<Employee>();
        }

        public void AddDepartment(Department department)
        {
            if (department != null)
            {
                foreach (Department dept in Departments)
                {
                    if (dept.Name == department.Name)
                    {
                        Console.WriteLine("Department already exists.");
                        return;
                    }
                }
                Departments.Add(department);
                Console.WriteLine($"Department '{department.Name}' added.");
            }
            else { 
                
                Console.WriteLine("we can't add this department"); 
            }
            
        }

        public void AddEmployee(Employee employee, string departmentName=null)
        {
            Department targetDepartment = null;
            foreach (Department dept in Departments)
            {
                if (dept.Name == departmentName)
                {
                    targetDepartment = dept;
                    break;
                }
            }
            //if (targetDepartment == null)
            //{
            //    Console.WriteLine("Department not found.");
            //    return;
            //}

            foreach (Employee emp in AllEmployees)
            {
                if (emp.ID == employee.ID)
                {
                    Console.WriteLine("Employee ID already exists.");
                    return;
                }
            }

            AllEmployees.Add(employee);
            

            if (targetDepartment != null) 
            {
                targetDepartment.AddEmployee(employee);
            }
        }
        public void ProcessPromotions(decimal salaryIncrease)
        {
            Console.WriteLine("\n=== Processing Promotions ===");
            int promotionsGiven = 0;

            foreach (Employee emp in AllEmployees)
            {
                if (emp.IsTerminated) continue;

                
                if (emp.PerformanceReviews.Count >= 3 && emp.GetAverageRating() >= 4)
                {
                    emp.Promote(salaryIncrease);
                    promotionsGiven++;
                }
            }

            Console.WriteLine($"Promoted {promotionsGiven} employees.");
        }

        public void GenerateDepartmentReport()
        {
            Console.WriteLine("\n=== Department Report ===");
            var table = new ConsoleTable("Department", "Employee Name", "Salary");
            foreach (Department dept in Departments)
            {
                foreach (Employee emp in dept.Employees)
                {
                    table.AddRow(dept.Name, emp.Name, emp.Salary);
                }
            }
            table.Write();
        }
        public void GenerateTopPerformersReport()
        {
            var table = new ConsoleTable("Rank", "Name", "Department", "Avg Rating", "Salary");

            List<Employee> activeEmployees = new List<Employee>();
            foreach (Employee emp in AllEmployees)
            {
                if (!emp.IsTerminated)
                    activeEmployees.Add(emp);
            }

           
            for (int i = 0; i < activeEmployees.Count - 1; i++)
            {
                for (int j = i + 1; j < activeEmployees.Count; j++)
                {
                    if (activeEmployees[i].GetAverageRating() < activeEmployees[j].GetAverageRating())
                    {
                        Employee temp = activeEmployees[i];
                        activeEmployees[i] = activeEmployees[j];
                        activeEmployees[j] = temp;
                    }
                }
            }

            // Display top 5
            int count = Math.Min(5, activeEmployees.Count);
            for (int i = 0; i < count; i++)
            {
                Employee emp = activeEmployees[i];
                table.AddRow(i + 1, emp.Name, emp.Department, emp.GetAverageRating().ToString("F1"), emp.Salary.ToString(""));
            }

            Console.WriteLine("\n=== Top Performers ===");
            table.Write(Format.Alternative);
        }

        public void GenerateSalaryReport()
        {
            var table = new ConsoleTable("Department", "Avg Salary", "Employees");

            Dictionary<string, decimal> deptSalaries = new Dictionary<string, decimal>();
            Dictionary<string, int> deptCounts = new Dictionary<string, int>();

            foreach (Employee emp in AllEmployees)
            {
                if (!deptSalaries.ContainsKey(emp.Department))
                {
                    deptSalaries[emp.Department] = 0;
                    deptCounts[emp.Department] = 0;
                }
                deptSalaries[emp.Department] += emp.Salary;
                deptCounts[emp.Department]++;
            }

            foreach (KeyValuePair<string, decimal> entry in deptSalaries)
            {
                string dept = entry.Key;
                decimal avgSalary = entry.Value / deptCounts[dept];
                table.AddRow(dept, avgSalary.ToString("C"), deptCounts[dept]);
            }

            Console.WriteLine("\n=== Salary Distribution ===");
            table.Write(Format.Alternative);
        }

        public void ViewEmployeePerformance(int employeeId)
        {
            Employee employee = null;
            foreach (Employee emp in AllEmployees)
            {
                if (emp.ID == employeeId)
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

            var table = new ConsoleTable("Date", "Rating", "Comments");

            if (employee.PerformanceReviews.Count == 0)
            {
                Console.WriteLine("No performance reviews yet.");
                return;
            }

            foreach (var review in employee.PerformanceReviews)
            {
                table.AddRow(review.ReviewDate.ToShortDateString(), review.Rating, review.Comments);
            }

            Console.WriteLine($"\nPerformance Reviews for {employee.Name} (Avg: {employee.GetAverageRating():F1}/5):");
            table.Write(Format.Alternative);
        }



        private const string ProjectPath = @"C:\Users\LEGION\source\repos\proj";
        private const string DefaultExportCsvPath = ProjectPath + @"\employees_export.csv";
        private const string DefaultImportCsvPath = ProjectPath + @"\employees_import.csv";
        private const string DefaultExcelExportPath = ProjectPath + @"\employees_export.xlsx";
        public const string AutoSavePath = ProjectPath + @"\auto_save.csv";

        public void ExportToCSV(string filePath = DefaultExportCsvPath)
        {
            try
            {
                // Ensure we have a valid file name
                if (Directory.Exists(filePath)) // If user provided only a directory
                {
                    filePath = Path.Combine(filePath, DefaultExportCsvPath);
                }

                // Create directory if needed
                string directory = Path.GetDirectoryName(filePath);
                if (!string.IsNullOrEmpty(directory) && !Directory.Exists(directory))
                {
                    Directory.CreateDirectory(directory);
                }

                using (StreamWriter writer = new StreamWriter(filePath))
                {
                    // Write header with proper escaping
                    writer.WriteLine("\"ID\",\"Name\",\"Age\",\"Salary\",\"Department\",\"EmploymentDate\",\"PerformanceRating\",\"IsTerminated\"");

                    // Write employee data with proper escaping
                    foreach (Employee emp in AllEmployees)
                    {
                        writer.WriteLine($"\"{emp.ID}\",\"{emp.Name}\",\"{emp.Age}\",\"{emp.Salary}\",\"{emp.Department}\",\"{emp.EmploymentDate}\",\"{emp.PerformanceRating}\",\"{emp.IsTerminated}\"");
                    }
                }
                Console.WriteLine($"Data exported successfully to {Path.GetFullPath(filePath)}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error exporting data: {ex.Message}");
                Console.WriteLine($"Attempted path: {Path.GetFullPath(filePath)}");
            }
        }

        public void ImportFromCSV(string filePath = DefaultImportCsvPath)
        {
            try
            {
                // If directory path was provided, use default filename in that directory
                if (Directory.Exists(filePath))
                {
                    filePath = Path.Combine(filePath, DefaultImportCsvPath);
                }

                if (!File.Exists(filePath))
                {
                    Console.WriteLine($"Error: File not found at {Path.GetFullPath(filePath)}");
                    return;
                }

                using (StreamReader reader = new StreamReader(filePath))
                {
                    // Skip header line
                    string headerLine = reader.ReadLine();
                    if (string.IsNullOrEmpty(headerLine))
                    {
                        Console.WriteLine("Error: Empty file or missing header");
                        return;
                    }

                    int lineNumber = 1;
                    while (!reader.EndOfStream)
                    {
                        lineNumber++;
                        string line = reader.ReadLine();

                        try
                        {
                            // Improved CSV parsing that handles quoted values
                            string[] values = ParseCsvLine(line);

                            // Validate we have all required columns
                            if (values.Length < 8)
                            {
                                Console.WriteLine($"Warning: Skipping incomplete record at line {lineNumber}");
                                continue;
                            }

                            // Create new employee with validation
                            Employee emp = new Employee(
                                id: int.Parse(values[0]),
                                name: values[1],
                                age: int.Parse(values[2]),
                                salary: decimal.Parse(values[3]),
                                department: values[4]
                            )
                            {
                                EmploymentDate = DateTime.Parse(values[5]),
                                PerformanceRating = double.Parse(values[6]),
                                IsTerminated = SafeParseBool(values[7])
                            };

                            // Add to company with validation
                            this.AddEmployee(emp, emp.Department);
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine($"Error processing line {lineNumber}: {ex.Message}");
                            Console.WriteLine($"Problematic line: {line}");
                        }
                    }
                }
                Console.WriteLine($"Data imported successfully from {Path.GetFullPath(filePath)}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error importing data: {ex.Message}");
                Console.WriteLine($"Attempted path: {Path.GetFullPath(filePath)}");
            }
        }

        public void ExportToExcel(string filePath = DefaultExcelExportPath)
        {
            try
            {
                // If directory path was provided, use default filename in that directory
                if (Directory.Exists(filePath))
                {
                    filePath = Path.Combine(filePath, DefaultExcelExportPath);
                }

                // Create directory if needed
                string directory = Path.GetDirectoryName(filePath);
                if (!string.IsNullOrEmpty(directory) && !Directory.Exists(directory))
                {
                    Directory.CreateDirectory(directory);
                }

                FileInfo file = new FileInfo(filePath);
                using (ExcelPackage package = new ExcelPackage(file))
                {
                    ExcelWorksheet worksheet = package.Workbook.Worksheets.Add("Employees");

                    // Add headers with formatting
                    worksheet.Cells[1, 1].Value = "ID";
                    worksheet.Cells[1, 2].Value = "Name";
                    worksheet.Cells[1, 3].Value = "Age";
                    worksheet.Cells[1, 4].Value = "Salary";
                    worksheet.Cells[1, 5].Value = "Department";
                    worksheet.Cells[1, 6].Value = "Employment Date";
                    worksheet.Cells[1, 7].Value = "Performance Rating";
                    worksheet.Cells[1, 8].Value = "Is Terminated";

                    // Format header row
                    using (var range = worksheet.Cells[1, 1, 1, 8])
                    {
                        range.Style.Font.Bold = true;
                        range.Style.Fill.PatternType = OfficeOpenXml.Style.ExcelFillStyle.Solid;
                        range.Style.Fill.BackgroundColor.SetColor(System.Drawing.Color.LightGray);
                    }

                    // Add data
                    int row = 2;
                    foreach (Employee emp in AllEmployees)
                    {
                        worksheet.Cells[row, 1].Value = emp.ID;
                        worksheet.Cells[row, 2].Value = emp.Name;
                        worksheet.Cells[row, 3].Value = emp.Age;
                        worksheet.Cells[row, 4].Value = emp.Salary;
                        worksheet.Cells[row, 5].Value = emp.Department;
                        worksheet.Cells[row, 6].Value = emp.EmploymentDate;
                        worksheet.Cells[row, 6].Style.Numberformat.Format = "yyyy-mm-dd";
                        worksheet.Cells[row, 7].Value = emp.PerformanceRating;
                        worksheet.Cells[row, 8].Value = emp.IsTerminated ? "Yes" : "No";
                        row++;
                    }

                    // Auto-fit columns for better readability
                    worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                    package.Save();
                }
                Console.WriteLine($"Data exported to Excel file: {Path.GetFullPath(filePath)}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error exporting to Excel: {ex.Message}");
                Console.WriteLine($"Attempted path: {Path.GetFullPath(filePath)}");
            }
        }

        private string[] ParseCsvLine(string line)
        {
            // Handle empty lines
            if (string.IsNullOrWhiteSpace(line))
                return new string[0];

            List<string> values = new List<string>();
            bool inQuotes = false;
            int startIndex = 0;

            for (int i = 0; i < line.Length; i++)
            {
                if (line[i] == '"')
                {
                    inQuotes = !inQuotes;
                }
                else if (line[i] == ',' && !inQuotes)
                {
                    string value = line.Substring(startIndex, i - startIndex)
                                  .Trim('"')
                                  .Trim();
                    values.Add(value);
                    startIndex = i + 1;
                }
            }

            // Add the last value
            string lastValue = line.Substring(startIndex)
                                 .Trim('"')
                                 .Trim();
            values.Add(lastValue);

            return values.ToArray();
        }

        private bool SafeParseBool(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                return false;

            value = value.Trim().ToLower();
            return value == "true" ||
                   value == "1" ||
                   value == "yes" ||
                   value == "y" ||
                   value == "t";
        }
    }

}
