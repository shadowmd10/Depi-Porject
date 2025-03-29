//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using System.Threading.Tasks;
//using OfficeOpenXml;
//using System;
//using System.Collections.Generic;
//using System.IO;
//using System;
//using System.Collections.Generic;
//using System.IO;
//namespace proj
//{


   

//    public class ExcelExporter
//    {
//        public static void ExportEmployeesToExcel(Company company)
//        {
//            ExcelPackage.LicenseContext = LicenseContext.NonCommercial;

//            // 📂 تحديد مسار حفظ الملف داخل "Documents"
//            string filePath = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments), "EmployeeReport.xlsx");

//            try
//            {
//                using (var package = new ExcelPackage())
//                {
//                    var worksheet = package.Workbook.Worksheets.Add("Employee Report");

                   
//                    worksheet.Cells[1, 1].Value = "Employee Name";
//                    worksheet.Cells[1, 2].Value = "Department";
//                    worksheet.Cells[1, 3].Value = "Salary";
//                    worksheet.Cells[1, 4].Value = "Performance Rating";
                    

//                    int row = 2; 

//                    foreach (Employee emp in company.AllEmployees)
//                    {
//                        worksheet.Cells[row, 1].Value = emp.Name;
//                        worksheet.Cells[row, 2].Value = emp.Department;
//                        worksheet.Cells[row, 3].Value = emp.Salary;
//                        worksheet.Cells[row, 4].Value = emp.PerformanceRating;
                       
//                        row++;
//                    }

//                    // ✅ حفظ الملف
//                    File.WriteAllBytes(filePath, package.GetAsByteArray());
//                    Console.WriteLine($"✅ تم حفظ تقرير الموظفين في: {filePath}");

//                    // 📂 فتح ملف Excel تلقائيًا بعد الحفظ
//                    System.Diagnostics.Process.Start("explorer.exe", filePath);
//                }
//            }
//            catch (Exception ex)
//            {
//                Console.WriteLine($"❌ خطأ أثناء إنشاء الملف: {ex.Message}");
//            }
//        }
//    }


//    public class ExcelImporter
//    {
//        public static void ImportEmployeesFromExcel(Company company)
//        {
//            string filePath = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments), "EmployeeReport.xlsx");

//            if (!File.Exists(filePath))
//            {
//                Console.WriteLine("Excel file not found. Starting with empty data.");
//                return;
//            }

//            try
//            {
//                ExcelPackage.LicenseContext = LicenseContext.NonCommercial;

//                using (ExcelPackage package = new ExcelPackage(new FileInfo(filePath)))
//                {
//                    ExcelWorksheet worksheet = package.Workbook.Worksheets[0];

//                    if (worksheet.Dimension == null)
//                    {
//                        Console.WriteLine("❌ The Excel file is empty. No data imported.");
//                        return;
//                    }

//                    int rowCount = worksheet.Dimension.Rows;
//                    int addedEmployees = 0;
//                    HashSet<int> existingEmployeeIds = new HashSet<int>();

//                    for (int row = 2; row <= rowCount; row++)
//                    {
//                        // قراءة البيانات باستخدام `Text` لضمان عدم إرجاع `null`
//                        string[] values = new string[6];
//                        for (int col = 1; col <= 6; col++)
//                        {
//                            values[col - 1] = worksheet.Cells[row, col].Text?.Trim();
//                        }

//                        // ✅ طباعة القيم لمعرفة ما يتم قراءته
//                        Console.WriteLine($"Row {row} Data: {string.Join(", ", values)}");

//                        if (values.Any(string.IsNullOrWhiteSpace))
//                        {
//                            Console.WriteLine($"⚠️ Warning: Missing values in row {row}. Skipping.");
//                            continue;
//                        }

//                        if (!int.TryParse(values[0], out int id) || existingEmployeeIds.Contains(id))
//                        {
//                            Console.WriteLine($"⚠️ Warning: Invalid or duplicate ID in row {row} ({values[0]}). Skipping.");
//                            continue;
//                        }

//                        if (!int.TryParse(values[2], out int age) ||
//                            !decimal.TryParse(values[3], out decimal salary) ||
//                            !double.TryParse(values[5], out double performanceRating))
//                        {
//                            Console.WriteLine($"⚠️ Warning: Invalid data in row {row}. Skipping.");
//                            continue;
//                        }

//                        Employee emp = new Employee(id, values[1], age, salary, values[4]);
//                        emp.UpdatePerformance(performanceRating);
//                        company.AddEmployee(emp, values[4]);
//                        existingEmployeeIds.Add(id);
//                        addedEmployees++;
//                    }

//                    Console.WriteLine(addedEmployees > 0
//                        ? $"✅ Employee data imported successfully. {addedEmployees} employees added."
//                        : "⚠️ No valid employees were imported.");
//                }
//            }
//            catch (Exception ex)
//            {
//                Console.WriteLine($"❌ Error importing data: {ex.Message}");
//            }
//        }
//    }



//}
