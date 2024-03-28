namespace Assignment2_PK_TT.Models.ViewModels
{
    public class SportClubSubscriptionViewModel
    {
        public string SportClubId { get; set; }
        public string Title { get; set; }
        public bool IsMember { get; set; }
        public Fan Fan { get; set; }
        public List<SportClub> SubscribedClubs { get; set; }
        public List<SportClub> UnsubscribedClubs { get; set; }


    }
}
