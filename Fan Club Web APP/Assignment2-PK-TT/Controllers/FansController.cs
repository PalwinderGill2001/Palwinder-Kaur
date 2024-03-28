using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Assignment2_PK_TT.Data;
using Assignment2_PK_TT.Models;
using Assignment2_PK_TT.Models.ViewModels;

namespace Assignment2_PK_TT.Controllers
{
    public class FansController : Controller
    {
        private readonly SportsDbContext _context;

        public FansController(SportsDbContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index(int? id)
        {
            var viewModel = new FanSubscriptionViewModel
            {
                Fans = await _context.Fans.Include(x => x.Subscriptions).OrderBy(x => x.Id).ToListAsync()
            };

            if (id != null)
            {
                viewModel.SportsClubs = await _context.SportClubs.Where(x => x.Subscriptions.Any(x => x.FanId == id)).ToListAsync();
            }

            return View(viewModel);
        }



        // GET: Fans/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Fans == null)
            {
                return NotFound();
            }

            var fan = await _context.Fans
                .FirstOrDefaultAsync(m => m.Id == id);
            if (fan == null)
            {
                return NotFound();
            }

            return View(fan);
        }

        // GET: Fans/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Fans/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,LastName,FirstName,BirthDate")] Fan fan)
        {
            if (ModelState.IsValid)
            {
                _context.Add(fan);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(fan);
        }

        // GET: Fans/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Fans == null)
            {
                return NotFound();
            }

            var fan = await _context.Fans.FindAsync(id);
            if (fan == null)
            {
                return NotFound();
            }
            return View(fan);
        }

        // POST: Fans/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,LastName,FirstName,BirthDate")] Fan fan)
        {
            if (id != fan.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(fan);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!FanExists(fan.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(fan);
        }

        // GET: Fans/EditSubscription/5
        public async Task<IActionResult> EditSubscription(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var fan = await _context.Fans
                .Include(f => f.Subscriptions)
                .ThenInclude(s => s.SportClub)
                .FirstOrDefaultAsync(m => m.Id == id);

            if (fan == null)
            {
                return NotFound();
            }

            var allSportClubs = await _context.SportClubs.ToListAsync();

            var viewModel = new SportClubSubscriptionViewModel
            {
                Fan = fan,
                SubscribedClubs = fan.Subscriptions.Select(s => s.SportClub).ToList(),
                UnsubscribedClubs = allSportClubs.Except(fan.Subscriptions.Select(s => s.SportClub)).OrderBy(s => s.Title).ToList()
            };

            return View(viewModel);
        }

        // POST: Fans/AddSubscription
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> AddSubscription(int fanId, string sportClubId)
        {
            var fan = await _context.Fans.FindAsync(fanId);
            if (fan == null)
            {
                return NotFound();
            }

            if (!_context.Subscriptions.Any(sub => sub.FanId == fanId && sub.SportClubId == sportClubId))
            {
                var newSubscription = new Subscription
                {
                    FanId = fan.Id,
                    SportClubId = sportClubId
                };
                _context.Subscriptions.Add(newSubscription);
                await _context.SaveChangesAsync();
            }

            return RedirectToAction("EditSubscription", new { id = fanId });
        }

        // POST: Fans/RemoveSubscription
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> RemoveSubscription(int fanId, string sportClubId)
        {
            var fan = await _context.Fans.FindAsync(fanId);
            if (fan == null)
            {
                return NotFound();
            }

            var subscriptionToRemove = await _context.Subscriptions
                .FirstOrDefaultAsync(sub => sub.FanId == fanId && sub.SportClubId == sportClubId);

            if (subscriptionToRemove != null)
            {
                _context.Subscriptions.Remove(subscriptionToRemove);
                await _context.SaveChangesAsync();
            }

            return RedirectToAction("EditSubscription", new { id = fanId });
        }


        // GET: Fans/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Fans == null)
            {
                return NotFound();
            }

            var fan = await _context.Fans
                .FirstOrDefaultAsync(m => m.Id == id);
            if (fan == null)
            {
                return NotFound();
            }

            return View(fan);
        }

        // POST: Fans/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Fans == null)
            {
                return Problem("Entity set 'SportsDbContext.Fans'  is null.");
            }
            var fan = await _context.Fans.FindAsync(id);
            if (fan != null)
            {
                _context.Fans.Remove(fan);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool FanExists(int id)
        {
          return _context.Fans.Any(e => e.Id == id);
        }
    }
}
