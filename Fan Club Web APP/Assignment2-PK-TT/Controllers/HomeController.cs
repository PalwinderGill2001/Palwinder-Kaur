using Microsoft.AspNetCore.Mvc;

namespace Assignment2_PK_TT.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            ViewBag.Message = "Welcome to our website!";

            return View();
        }
        public IActionResult Error()
        {
            return View();
        }
    }
}
