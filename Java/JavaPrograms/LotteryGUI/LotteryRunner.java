
public class LotteryRunner {
    public static void main(String[] args) {
        LotteryGame game = new LotteryGame();

        game.createLottoButtons();
        game.createOutputFields();
        game.createCheckBoxes();
        game.CreateBiddingNumbers();
        game.createFirstFrame();
    }

}
