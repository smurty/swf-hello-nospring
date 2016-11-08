package hello;

public class GreeterActivitiesImpl implements GreeterActivities {
   @Override
   public String getName() throws InterruptedException {
      System.out.println("ACTIVITY: getName");
      Thread.sleep(5000);
      return "World";
   }
   @Override
   public void say(String what) {
      System.out.println("ACTIVITY: say");
      System.out.println(what);
   }
}

