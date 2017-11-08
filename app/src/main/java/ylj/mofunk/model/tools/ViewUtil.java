package ylj.mofunk.model.tools;

import android.view.View;

public class ViewUtil {

    public  static final double design_height = 1280  ;  //设计图的高度
    public  static final double design_width = 720  ; //设计图的宽度
   
    public  static  double screen_width = 0  ; //实际屏幕宽度
    public  static  double screen_height = 0  ; //实际屏幕高度
    
    
    
    /**************************************************************************
   	 *  计算大小 
   	 *********************************************************************** */
    public static  int getSizeWidth(double width) {  
		return (int) (width*(screen_width/design_width));
    } 
    public static  int getSizeHeight(double height) {  
		return (int) (height*(screen_height/design_height));
    } 
    
    
    
    
    
    
    /**************************************************************************
   	 * 设置实际屏幕的宽�??
   	 * @width 屏幕宽度
   	 *********************************************************************** */
    public static  void setScreenWidth(double width) {  
    		screen_width=width;
    }  
    
    
    /**************************************************************************
   	 * 设置实际屏幕的高�??
   	 * @height 屏幕高度
   	 *********************************************************************** */
    public static  void setScreenHeight(double height) {  
    		screen_height=height;
    }   
    /**************************************************************************
	 * 使用控件尺寸计算控件的尺�??
	 * @view 控件
	 * @width 控件设计宽度大小
	 * @height 控件设计高度大小
	 *********************************************************************** */
	public static void  setPicSize(View view  , double width, double height){
		view.getLayoutParams().height = (int) (height*(screen_height/design_height));
		view.getLayoutParams().width = (int) (width*(screen_width/design_width));
	}
    
    
    /**************************************************************************
	 * 使用控件尺寸计算控件的高�??
	 * @view 控件
	 * @height 控件设计大小
	 *********************************************************************** */
	public static void  setHeight(View view , double height){
		view.getLayoutParams().height = (int) (height*(screen_height/design_height));
	}
	/**************************************************************************
	 * 使用控件尺寸计算控件的宽�??
	 * @view 控件
	 * @width 控件设计大小
	 *********************************************************************** */
	public static void  setWidth(View view , double width){
		view.getLayoutParams().width = (int) (width*(screen_width/design_width));
	}
	

    /**************************************************************************
	 * 使用控件比例计算控件的高�??
	 * @view 控件
	 * @rate 控件设计比例
	 *********************************************************************** */
	public static void  setHeight_UseRate(View view , double rate){
		view.getLayoutParams().height = (int) (rate*screen_height);
	}
	
	/**************************************************************************
	 * 使用控件比例计算控件的宽�??
	 * @view 控件
	 * @rate 控件设计比例
	 *********************************************************************** */
	public static void  setWidth_UseRate(View view , double rate){
		view.getLayoutParams().width =  (int) (rate*screen_width);
	}
	
	
	
}
