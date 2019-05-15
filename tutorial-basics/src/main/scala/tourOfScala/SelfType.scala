package tourOfScala

trait User {
  def username: String
}

trait Tweeter {
  this: User =>
  def tweet(tweetText: String) = println(s"$username: $tweetText")
}

class VerifiedTweeter(val username_ : String) extends Tweeter with User {
  def username = s"real $username_"
}

object SelfType extends App {

  val realTweet = new VerifiedTweeter("Micheal Jackson")
  realTweet.tweet("Thriller")

}
