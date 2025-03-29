using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proj
{
    public class PerformanceReview
    {
        public Employee Employee { get; set; }
        public double Rating { get; set; }
        public DateTime ReviewDate { get; set; }
        public string Comments { get; set; }

        public PerformanceReview(Employee employee, double rating, string comments)
        {
            if (rating < 0 || rating > 5)
                throw new ArgumentException("Rating must be between 0 and 5.");

            Employee = employee;
            Rating = rating;
            Comments = comments;
            ReviewDate = DateTime.Now;
        }

        
        public bool IsEligibleForPromotion()
        {
            return (Rating >= 4 && !Employee.IsTerminated);
        }
    }
}
