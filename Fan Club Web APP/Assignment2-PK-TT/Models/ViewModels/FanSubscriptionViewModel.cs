namespace Assignment2_PK_TT.Models.ViewModels
{
    public class FanSubscriptionViewModel
    {
            public Fan Fan { get; set; }

            public IEnumerable<Fan> Fans { get; set; }

            public IEnumerable<SportClub> SportsClubs { get; set; }
            public IEnumerable<SportClubSubscriptionViewModel> Subscriptions { get; set; }

    }
}
