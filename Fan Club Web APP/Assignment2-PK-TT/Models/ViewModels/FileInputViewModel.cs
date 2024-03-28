using System.ComponentModel.DataAnnotations;

namespace Assignment2_PK_TT.Models.ViewModels
{
    public class FileInputViewModel
    {
     
        public string SportClubId { get; set; }

        
        public string SportClubTitle { get; set; }

        public IFormFile File { get; set; }

    }
}
