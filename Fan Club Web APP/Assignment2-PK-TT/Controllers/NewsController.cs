using Assignment2_PK_TT.Models.ViewModels;
using Assignment2_PK_TT.Models;
using Azure.Storage.Blobs;
using Azure;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Assignment2_PK_TT.Data;
using Assignment2_PK_TT.Migrations;
using System.Reflection.Metadata;
using System;

namespace Assignment2_PK_TT.Controllers
{

    public class NewsController : Controller
    {
        private readonly SportsDbContext _context;
        private readonly BlobServiceClient _blobServiceClient;

        public NewsController(SportsDbContext context, BlobServiceClient blobServiceClient)
        {
            _context = context;
            _blobServiceClient = blobServiceClient;
        }

        public IActionResult Index(string id)
        {
            var sportClub = _context.SportClubs.Include(sc => sc.News).FirstOrDefault(sc => sc.Id == id);
            if (sportClub == null)
            {
                return NotFound();
            }

            var viewModel = new NewsViewModel
            {
                SportClub = sportClub,
                News = sportClub.News
            };

            return View(viewModel);
        }

        public IActionResult Create(string id)
        {
            var sportClub = _context.SportClubs.FirstOrDefault(sc => sc.Id == id);
            if (sportClub == null)
            {
                return NotFound();
            }

            var viewModel = new FileInputViewModel
            {
                SportClubId = sportClub.Id,
                SportClubTitle = sportClub.Title
            };

            return View(viewModel);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(FileInputViewModel viewModel)
        {
            var sportClub = await _context.SportClubs.FirstOrDefaultAsync(sc => sc.Id == viewModel.SportClubId);

            if (sportClub == null)
            {
                return NotFound();
            }

            viewModel.SportClubTitle = sportClub.Title;//  Set the SportClubTitle property using the title from the SportClub model

            BlobContainerClient containerClient;

            // Check if a file was selected
            if (viewModel.File == null || viewModel.File.Length == 0)
            {
                ViewData["ErrorMessage"] = "Select the file first";
                return View(viewModel); // Return to the view with the validation error message
            }
            try
            {
                containerClient = await _blobServiceClient.CreateBlobContainerAsync(viewModel.SportClubTitle.ToLower());
                //  containerClient = await _blobServiceClient.CreateBlobContainerAsync(viewModel.SportClubId);
                containerClient.SetAccessPolicy(Azure.Storage.Blobs.Models.PublicAccessType.BlobContainer);
            }
            catch (RequestFailedException)
            {
                containerClient = _blobServiceClient.GetBlobContainerClient(viewModel.SportClubTitle.ToLower());
                // containerClient = _blobServiceClient.GetBlobContainerClient(viewModel.SportClubId);
            }

            try
            {
                string randomFileName = Path.GetRandomFileName();
                var blockBlob = containerClient.GetBlobClient(randomFileName);
            }
            catch (RequestFailedException)
            {
                return View("Error");
            }


            try
            {
                string randomFileName = Path.GetRandomFileName();
                var blockBlob = containerClient.GetBlobClient(randomFileName);
                if (await blockBlob.ExistsAsync())
                {
                    await blockBlob.DeleteAsync();
                }

                using (var memoryStream = new MemoryStream())
                {
                    await viewModel.File.CopyToAsync(memoryStream);
                    memoryStream.Position = 0;
                    await blockBlob.UploadAsync(memoryStream);
                    memoryStream.Close();
                }

                var news = new News
                {
                    FileName = viewModel.File.FileName,
                    Url = blockBlob.Uri.ToString(), // Using Blob URL as the image URL
                    SportClubId = sportClub.Id
                };

                _context.News.Add(news);
                await _context.SaveChangesAsync();

            }
            catch (RequestFailedException)
            {
                return View("Error");
            }

            return RedirectToAction("Index", new { id = sportClub.Id });
        }

        public async Task<IActionResult> Delete(int id)
        {
            var news = await _context.News.FindAsync(id);
            if (news == null)
            {
                return NotFound();
            }

            return View(news);
        }

        [HttpPost, ActionName("DeleteConfirmed")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            try
            {
                // Retrieve the news article to be deleted
                var news = await _context.News
                    .Include(n => n.SportClub) // Include the SportClub navigation property
                    .FirstOrDefaultAsync(n => n.Id == id);

                if (news == null)
                {
                    return NotFound();
                }

                // Delete the associated blob from blob storage
                BlobContainerClient containerClient = _blobServiceClient.GetBlobContainerClient(news.SportClub.Title.ToLower());
                if (await containerClient.ExistsAsync())
                {
                    var blobName = news.FileName; // Use the FileName property as the blob name
                    var blockBlob = containerClient.GetBlobClient(blobName);

                    if (await blockBlob.ExistsAsync())
                    {
                        await blockBlob.DeleteAsync();
                    }
                }

                // Delete the news entry from the database
                _context.News.Remove(news);
                await _context.SaveChangesAsync();

                return RedirectToAction("Index", new { id = news.SportClub.Id }); // Redirect to the specific sport club's news index
            }
            catch (RequestFailedException)
            {
                return View("Error");
            }
        }

    }
}