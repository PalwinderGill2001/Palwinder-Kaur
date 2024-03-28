namespace Assignment2_PK_TT.Models.ViewModels
{
    public class SportClubViewModel
    {
        public IEnumerable<Fan> Fans { get; set; }

        public IEnumerable<SportClub> SportsClubs { get; set; }

        public IEnumerable<Subscription> Subscriptions { get; set; }
        public IEnumerable<News> News { get; set; }
    }
}

