using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Assignment2_PK_TT.Models
{
    public class News
    {
       [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public string FileName
        {
            get;
            set;
        }

        [StringLength(50, MinimumLength =1)]
        public string Url
        {
            get;
            set;
        }

        public string SportClubId { get; set; } // Foreign key
        public SportClub SportClub { get; set; } // Navigation property

    }
}
