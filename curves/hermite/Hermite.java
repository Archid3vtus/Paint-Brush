/*------------------------------------------------------------------------------
 * Hermite Curve: Drawing Curve using Hermite Algorithm
 * Advanced Computer Graphics
 * Written by: Kevin T. Duraj
 */
package curves.hermite;

import components.PaintPnl;
import java.awt.*;

/*------------------------------------------------------------------------------
 * Class Hermite using Bresenham between points
 */
public class Hermite {
  private PaintPnl paintPnl;
  private Point P0, P1, P2, P3;

  public Hermite(PaintPnl paintPnl) {
    this.paintPnl = paintPnl;
  }

  public void setP0(Point p0) {
    P0 = p0;
  }

  public void setP1(Point p1) {
    P1 = p1;
  }

  public void setP2(Point p2) {
    P2 = p2;
  }

  public void setP3(Point p3) {
    P3 = p3;
  }

  /*--------------------------------------------------------------------------
   *  Compute Hemite Cubic Points Derivatives 
   *  +-----------------+   +-----------+
   *  |  2  -2   1   1  |   |  x0   y0  |
   *  | -3   3  -2  -1  | * |  x1   y1  | * [t^3 t^2 t 1] = [x, y]
   *  |  0   0   1   0  |   |  x'0  y'0 |
   *  |  1   0   0   0  |   |  x'1  y'1 |
   *  +-----------------+   +-----------+
   -------------------------------------------------------------------------*/

  public Point[] cubic() {

    Point array[] = { new Point((1 * P0.x) + (0) + (0) + (0), (1 * P0.y) + (0) + (0) + (0)),
        new Point((0) + (0) + (1 * P2.x) + (0), (0) + (0) + (1 * P2.y) + (0)),
        new Point((-3 * P0.x) + (3 * P1.x) + (-2 * P2.x) + (-1 * P3.x),
            (-3 * P0.y) + (3 * P1.y) + (-2 * P2.y) + (-1 * P3.y)),
        new Point((2 * P0.x) + (-2 * P1.x) + (1 * P2.x) + (1 * P3.x),
            (2 * P0.y) + (-2 * P1.y) + (1 * P2.y) + (1 * P3.y)) };

    return array;

  }
  /*------------------------------------------------------------------------
   * Create Steps Method
   * Increasing steps make curve smooth
   * Calculate "z" as 3rd dimension
   */

  public void steps(double step) {
    // Compute Vector
    P2 = new Point(P2.x - P0.x, P2.y - P0.y);
    P3 = new Point(P3.x - P1.x, P3.y - P1.y);

    Point array[] = cubic();

    Point array2[] = new Point[(int) (1 / step)];
    int i = 0;

    for (double u = 0.00; u < 1; u += step) {
      array2[i] = new Point((int) (array[0].x + array[1].x * u + array[2].x * u * u + array[3].x * u * u * u),
          (int) (array[0].y + array[1].y * u + array[2].y * u * u + array[3].y * u * u * u));

      paintPnl.setPixel(array2[i].x, array2[i].y);
      i++;

    }

    int j;
    for (j = 0; j < (int) (1 / step) - 1; j++) {

      paintPnl.Bresenham(array2[j].x, array2[j].y, array2[j + 1].x, array2[j + 1].y);
    }

    // Draw the last line to the end point

    paintPnl.Bresenham(array2[j].x, array2[j].y, P1.x, P1.y);

  }
}

/*----------------------------------------------------------------------------*/