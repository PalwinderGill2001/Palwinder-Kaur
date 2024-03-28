using Assignment2_PK_TT.Models;
using Microsoft.EntityFrameworkCore;

namespace Assignment2_PK_TT.Data
{
    public class SportsDbContext : DbContext
    {
        public SportsDbContext(DbContextOptions<SportsDbContext> options) : base(options)
        {
        }

        public DbSet<Fan> Fans { get; set; }
        public DbSet<SportClub> SportClubs { get; set; }

        public DbSet<News> News { get; set; }
        public DbSet<Subscription> Subscriptions { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Fan>().ToTable("Fan");
            modelBuilder.Entity<SportClub>().ToTable("SportClub");
            modelBuilder.Entity<News>().ToTable("News");
            modelBuilder.Entity<Subscription>().ToTable("Subscription")
                .HasKey(s => new { s.FanId, s.SportClubId });

            // Define the relationship between SportClub and News entities
            modelBuilder.Entity<News>()
                .HasOne(n => n.SportClub)
                .WithMany(sc => sc.News)
                .HasForeignKey(n => n.SportClubId)
              .OnDelete(DeleteBehavior.Cascade); // Configure cascade delete
        }

    }
}
