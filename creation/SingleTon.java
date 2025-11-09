package creation;
public class SingleTon {
    
}

//Eager loading and thread safe
class JudgeAnalytics {
    private static final JudgeAnalytics instance = new JudgeAnalytics();

    private JudgeAnalytics() {
    }

    public static JudgeAnalytics getInstance() {
        return instance;
    }
}
//Lazy loading and not thread safe
class JudgeAnalytics2 {
    private static JudgeAnalytics2 instance;

    private JudgeAnalytics2() {
    }

    public static JudgeAnalytics2 getInstance() {
        if(instance == null) {
            instance = new JudgeAnalytics2();
        }
        return instance;
    }
}

//Lazy loading and thread safe but performance is not good because of the synchronized keyword
class JudgeAnalytics3 {
    private static JudgeAnalytics3 instance;

    private JudgeAnalytics3() {
    }

    public static synchronized JudgeAnalytics3 getInstance() {
        if(instance == null) {
            instance = new JudgeAnalytics3();
        }
        return instance;
    }
}

//Lazy loading and thread safe but performance is good because of the double check locking and volatile keyword
class JudgeAnalytics4 {
    private static volatile JudgeAnalytics4 instance;

    private JudgeAnalytics4() {
    }

    public static synchronized JudgeAnalytics4 getInstance() {
        if(instance == null) {
            synchronized(JudgeAnalytics4.class) {
                if(instance == null) {
                    instance = new JudgeAnalytics4();
                }
            }
        }
        return instance;
    }
}

//Lazy loading and thread safe but performance is good because of the static inner class 
class JudgeAnalytics5 {

    private JudgeAnalytics5() {
    }
    private static class JudgeAnalytics5Holder {
        private static final JudgeAnalytics5 instance = new JudgeAnalytics5();
    }
    public static synchronized JudgeAnalytics5 getInstance() {
       return JudgeAnalytics5Holder.instance;
    }
}