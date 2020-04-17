package clipping;

/**
* Classe responsavel pelo algoritmo de recorte de regi�es, CohenSutherland
*
* @author Ian
* @author Saul Melo
* @author Yuri
* @since 04 de 2020 
* @version 1
*/

public class CohenSutherland implements LineClipper {

  public static final int INSIDE = 0;
  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  public static final int BOTTOM = 4;
  public static final int TOP = 8;

  private int xMin;
  private int xMax;
  private int yMin;
  private int yMax;

  /**
  * Construtor parametrizado da classe
  *
  * @param int, O menor valor da coordenada x pertencente a regiao
  * @param int, O menor valor da coordenada y pertencente a regiao
  * @param int, O maior valor da coordenada x pertencente a regiao
  * @param int, O maior valor da coordenada y pertencente a regiao
  */
  public CohenSutherland(int xMin, int yMin, int xMax, int yMax) {
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
  }


  /**
  * Este metodo determina a codificacao da regiao utilizada pelo algoritmo
  * De Cohen-Sutherland para o recorte de regioes
  *
  * @param LineSegment, Objeto do tipo LineSegment contendo as dimensoes da janela
  */
  private int OutCode(double x, double y) {
    int code = INSIDE;

    if (x < xMin) {
      code |= LEFT;
    } else if (x > xMax) {
      code |= RIGHT;
    }
    if (y < yMin) {
      code |= BOTTOM;
    } else if (y > yMax) {
      code |= TOP;
    }
    return code;
  }

  /**
  * Algoritmo de Cohen-Sutherland para o recorte de regioes
  *
  * @param LineSegment, Objeto do tipo LineSegment contendo as dimensoes da janela
  */
  public LineSegment clip(LineSegment line) {
    System.out.println("\nExecuting Cohen-Sutherland...");
    int x0 = line.x0, x1 = line.x1, y0 = line.y0, y1 = line.y1;
    int outCode0 = OutCode(x0, y0);
    int outCode1 = OutCode(x1, y1);
    System.out.println("OutCode0: " + outCode0 + ", OutCode1: " + outCode1);
    boolean accept = false;

    while (true) {
      if ((outCode0 | outCode1) == 0) {
        accept = true;
        break;
      } else if ((outCode0 & outCode1) != 0) {
        break;
      } else {
        int x, y;

        int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;

        if ((outCodeOut & TOP) != 0) {
          x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
          y = yMax;
        } else if ((outCodeOut & BOTTOM) != 0) {
          x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
          y = yMin;
        } else if ((outCodeOut & RIGHT) != 0) {
          y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
          x = xMax;
        } else {
          y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
          x = xMin;
        }

        if (outCodeOut == outCode0) {
          x0 = x;
          y0 = y;
          outCode0 = OutCode(x0, y0);
        } else {
          x1 = x;
          y1 = y;
          outCode1 = OutCode(x1, y1);
        }
      }
    }
    if (accept) {
      return new LineSegment(x0, y0, x1, y1);
    }
    return null;
  }

}