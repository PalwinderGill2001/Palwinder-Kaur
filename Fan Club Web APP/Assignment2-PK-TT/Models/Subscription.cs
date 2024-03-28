namespace Assignment2_PK_TT.Models
{
    public class Subscription
    {

        public int FanId { get; set; }
        public string SportClubId { get; set; }

        public Fan Fan { get; set; }

        public SportClub SportClub { get; set; }
    }
}
