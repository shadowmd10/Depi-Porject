using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace proj
{
    

    public class Employee
    {
        public int ID { get; private set; }
        public string Name { get; set; }
        public int Age { get; set; }
        public decimal Salary { get; set; }
        public string Department { get; set; }
        public DateTime EmploymentDate { get; set; }
        public double PerformanceRating { get; set; }
        public bool IsTerminated { get;  set; }
        public object YearsOfExperience { get; internal set; }
        public List<PerformanceReview> PerformanceReviews { get; private set; }
        public Employee(int id, string name, int age, decimal salary, string department)
        {
            ID = id;
            Name = name;
            Age = age;
            Salary = salary;
            Department = department;
            EmploymentDate = DateTime.Now;
            PerformanceRating = 0;
            IsTerminated = false;
            PerformanceReviews = new List<PerformanceReview>();
        }

        public void Promote(decimal salaryIncrease)
        {
            if (IsTerminated)
            {
                Console.WriteLine("Cannot promote a terminated employee.");
                return;
            }
            if (GetAverageRating() >= 4)
            {
                Salary += salaryIncrease;
            }
            else {

                Console.WriteLine("avg Rating under 4");
                return ; 
            }
            Console.WriteLine($"{Name} has been promoted. New salary: ${Salary}");
        }

     
        public void TransferDepartment(Department newDepartment,Company c)
        {
            if (IsTerminated)
            {
                Console.WriteLine("Cannot transfer a terminated employee.");
                return;
            }
            Department d=null;
            foreach (var x in c.Departments) {

                if (x.Name == this.Department) { 
                
                d = x;
                
                
                }     
            
            
            }
            string oldDepartment = newDepartment.Name;
           
            if (newDepartment != null) {

                d.RemoveEmployee(this);
            }
            this.Department = newDepartment.Name;
            newDepartment.AddEmployee(this);
            Console.WriteLine($"{Name} has been transferred from {oldDepartment} to {newDepartment}.");
        }
        
        public void Terminate()
        {
            if (IsTerminated)
            {
                Console.WriteLine("Employee is already terminated.");
                return;
            }
            IsTerminated = true;
            Console.WriteLine($"{Name} has been terminated.");
        }

        public void AddPerformanceReview(double rating, string comments)
        {
            var review = new PerformanceReview(this, rating, comments);
            PerformanceReviews.Add(review);
            Console.WriteLine($"Added performance review for {Name}: Rating={rating}, Comments='{comments}'");
        }

        
        public double GetAverageRating()
        {
            if (PerformanceReviews.Count == 0) return 0;

            double total = 0;
            foreach (var review in PerformanceReviews)
            {
                total += review.Rating;
            }
            return total / PerformanceReviews.Count;
        }
       
    }
}
