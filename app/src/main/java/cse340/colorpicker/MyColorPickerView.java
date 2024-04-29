package cse340.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Switch;

import androidx.constraintlayout.solver.widgets.Rectangle;

/**
 * TODO: Implement your ColorPickerView. You will be expected to use appropriate helper methods and add comments
 */
public class MyColorPickerView extends ColorPickerView {

    /**
     * Update the local model (color) for this colorpicker view
     *
     * @param x The x location that the user selected
     * @param y The y location that the user selected
     */
    protected void updateModel(float x, float y) {
        // TODO implement this
        //setPickerColor() (calculate color based off position and then call setPickerColor)
        setPickerColor(getQuadrantColor(getQuadrant(x, y)));
    }

    /* ********************************************************************************************** *
     *                               <End of model declarations />
     * ********************************************************************************************** */

    /* ********************************************************************************************** *
     * TODO Create variables you might need
     * You may create any constants you wish here.                                                     *
     * You may also create any fields you want, that are not necessary for the state but allow         *
     * for better optimized or cleaner code                                                             *
     * ********************************************************************************************** */
    /**The x position of the center of the screen*/
    private int mScreenCenterX = 0;
    /**The y position of the center of the screen*/
    private int mScreenCenterY = 0;
    /**The distance from the center of the screen each side the colorPicker is*/
    private int mRadius;
    /**Paint objects for the green, purple, blue, and yellow parts of the colorPicker*/
    private Paint mPaintBrushGreen;
    private Paint mPaintBrushPurple;
    private Paint mPaintBrushBlue;
    private Paint mPaintBrushYellow;


    /* ********************************************************************************************** *
     *                               <End of other fields and constants declarations />
     * ********************************************************************************************** */

    /**
     *
     * Constructor of the ColorPicker View
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view. This value may be null.
     */
    public MyColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaintBrushGreen = new Paint();
        mPaintBrushGreen.setColor(Color.GREEN);

        mPaintBrushBlue = new Paint();
        mPaintBrushBlue.setColor(Color.BLUE);

        mPaintBrushYellow = new Paint();
        mPaintBrushYellow.setColor(Color.YELLOW);

        mPaintBrushPurple = new Paint();
        mPaintBrushPurple.setColor(Color.MAGENTA);

    }

    /**
     * Draw the ColorPicker on the Canvas
     * @param canvas the canvas that is drawn upon
     */
    @Override
    protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);

       int left = canvas.getWidth()/2-mRadius;
       int right = canvas.getWidth()/2+mRadius;
       int top = canvas.getHeight()/2-mRadius;
       int bottom = canvas.getHeight()/2+mRadius;


        canvas.drawRect(left, top, right-mRadius, bottom-mRadius, mPaintBrushBlue);
        canvas.drawRect(left+mRadius, top, right, bottom-mRadius, mPaintBrushGreen);
        canvas.drawRect(left, top+mRadius, right-mRadius, bottom, mPaintBrushYellow);
        canvas.drawRect(left+mRadius, top+mRadius, right, bottom, mPaintBrushPurple);
    }


    /**
     * Called when this view should assign a size and position to all of its children.
     * @param changed This is a new size or position for this view
     * @param left Left position, relative to parent
     * @param top Top position, relative to parent
     * @param right Right position, relative to parent
     * @param bottom Bottom position, relative to parent
     */
    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int length = Math.min(right-left, bottom-top);
        mRadius = length/2;
        mScreenCenterX = mRadius;
        mScreenCenterY = mRadius;
    }



    /**
     * Calculate the essential geometry given an event.
     *
     * @param event Motion event to compute geometry for, most likely a touch.
     * @return EssentialGeometry value.
     */

    @Override
    protected EssentialGeometry essentialGeometry(MotionEvent event) {
       float xPosition = event.getX();
       float yPosition = event.getY();

        if((xPosition >= mScreenCenterX-mRadius && xPosition <= mScreenCenterX+mRadius) &&
                (yPosition >= mScreenCenterY-mRadius && yPosition <= mScreenCenterY+mRadius)) {
            return EssentialGeometry.INSIDE;
        } else {
            return EssentialGeometry.OUTSIDE;
        }
   }

    /* ********************************************************************************************** *
     *                               <Helper Functions />
     *           TODO add helper functions as needed (i.e. use good coding practice)
     * ********************************************************************************************** */


    /** Determines which quadrant of the screen a point is with the intent of determining which color
     * from the square color picker should be selected.
     *
     * @param x horizontal position of the touch event.
     * @param y vertical position of the touch event.
     * @return screen quadrant the touch event occurs in
     */
    public int getQuadrant(float x, float y) {
        if(x > mScreenCenterX && y < mScreenCenterY) {
            return 1;
        } else if (x < mScreenCenterX && y < mScreenCenterY) {
            return 2;
        } else if ( x < mScreenCenterX && y > mScreenCenterY) {
            return 3;
        } else {
            return 4;
        }
    }


    /** Determines the color being selected based off of which quadrant the touch event occurs in.
     *
     * @param quadrant The quadrant the touch event occurs in.
     * @return The color associated with the inputted quadrant.
     */
    public int getQuadrantColor(int quadrant) {
        if(quadrant == 1) {
            return Color.GREEN;
        } else if(quadrant == 2) {
            return Color.BLUE;
        } else if(quadrant == 3) {
            return Color.YELLOW;
        } else {
            return Color.MAGENTA;
        }
    }

}
