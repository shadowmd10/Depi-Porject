using ConsoleTables;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proj
{


    public class Department
    {
        public string Name { get; set; }
        public string DepartmentHead { get; set; }
        public List<Employee> Employees { get; private set; }

        public Department(string name, string departmentHead)
        {
            Name = name;
            DepartmentHead = departmentHead;
            Employees = new List<Employee>();
        }

        public void AddEmployee(Employee employee)
        {
            foreach (Employee emp in Employees)
            {
                if (emp.ID == employee.ID)
                {
                    Console.WriteLine("Employee already exists in the department.");
                    return;
                }
            }
            Employees.Add(employee);
            Console.WriteLine($"{employee.Name} has been added to {Name}.");
        }

        public void RemoveEmployee(Employee employee)
        {
            if (!Employees.Contains(employee))
            {
                Console.WriteLine("Employee not found in the department.");
                return;
            }
            Employees.Remove(employee);
            //Company c;
            //c.AllEmployees.Remove(employee);
            Console.WriteLine($"{employee.Name} has been removed from {Name}.");
        }
        //public void empPerDepartment()
        //{
        //    foreach (Employee employee in Employees)
        //    {
        //        Console.WriteLine(employee.ToString());
        //    }
        //}

        public void ListEmployees()
        {
            var table = new ConsoleTable("ID", "Name", "Age", "Salary", "Performance", "Status");

            if (Employees.Count == 0)
            {
                Console.WriteLine("No employees in this department.");
                return;
            }

            foreach (Employee emp in Employees)
            {
                string status = emp.IsTerminated ? "Terminated" : "Active";
                table.AddRow(emp.ID, emp.Name, emp.Age, emp.Salary.ToString("C"), emp.PerformanceRating + "/5", status);
            }

            Console.WriteLine($"\nEmployees in {Name} (Head: {DepartmentHead}):");
            table.Write(Format.Alternative);
        }
    }
}
