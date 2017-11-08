package ylj.mofunk.model.Base;

import android.app.Activity;

import java.util.Stack;

public class ActivityManager {
	
    private static Stack<Activity> activityStack;
    private static ActivityManager instance; 
    private ActivityManager() { 
    } 
    public static ActivityManager getScreenManager() { 
        if (instance == null) { 
            instance = new ActivityManager(); 
        } 
        return instance; 
    } 
    public void popActivity(Activity activity) {
        if (activity != null) { 
            activity.finish(); 
            activityStack.remove(activity); 
            activity = null; 
        } 
    } 
    
    public Activity currentActivity() {
        Activity activity = null;
       if(!activityStack.empty()) 
         activity= activityStack.lastElement(); 
        return activity; 
    } 
    public void pushActivity(Activity activity) {
        if (activityStack == null) { 
            activityStack = new Stack<Activity>();
        } 
        activityStack.add(activity); 
    } 
    public void popAllActivityExceptOne(Class<?> cls) {
        while (true) { 
            Activity activity = currentActivity();
            if (activity == null) { 
                break; 
            } 
            if (activity.getClass().equals(cls)) { 
                break; 
            } 
            popActivity(activity); 
        } 
    } 
    
    public  void getAllActivity(){
    	
    	for(Activity a:activityStack){
    		System.out.println("ac="+a.getClass().getName());
    	}
    }
    

    public void popAllActivityExceptTwo(Class<?> cls0, Class<?> cls1) {
        while (true) { 
            Activity activity = currentActivity();
            if (activity == null) { 
                break; 
            }else{
            	  if (!activity.getClass().equals(cls0)&&!activity.getClass().equals(cls1)) { 
            		   popActivity(activity); 
               } 
            }
        
         
        } 
    } 
    

    public void getallActivity() { 
       for(int i=0;i<activityStack.size();i++){
    	   System.out.println(i+"="+((Activity)activityStack.get(i)).toString());
       }
    } 
    
} 

