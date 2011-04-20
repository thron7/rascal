package org.rascalmpl.library.vis;

import java.util.ArrayList;

public class Interpolation {
    final static boolean debug=false;
	static float[] lD, uD, D, z, v, h, x, y;
	static TypedPoint[] P0, P1, P2, P3;

	// Tridiagonal matrix algorithm Solve Av = c;
	static void solveMatrix(float[] a, float[] b, float[] c, float[] v,
			float[] x) {
		/**
		 * n - number of equations a - sub-diagonal (means it is the diagonal
		 * below the main diagonal) b - the main diagonal c - sup-diagonal
		 * (means it is the diagonal above the main diagonal) v - right part x -
		 * the answer
		 */
		int n = b.length;
		if (debug) System.err.println("Sizes:" + x.length + " " + v.length + " "
				+ b.length);
		for (int i = 1; i < n; i++) {
			float m = a[i] / b[i - 1];
			b[i] = b[i] - m * c[i - 1];
			v[i] = v[i] - m * v[i - 1];
		}

		x[n - 1] = v[n - 1] / b[n - 1];

		for (int i = n - 2; i >= 0; i--)
			x[i] = (v[i] - c[i] * x[i + 1]) / b[i];
	}

	static boolean computeMatrix(ArrayList<TypedPoint> r) {
		x = new float[r.size()];
		y = new float[r.size()];
		int n = 0;
//		System.err.println("r.size=" + r.size());
		while (!r.isEmpty()) {
			TypedPoint p = r.get(0);
			if (p.curved==TypedPoint.kind.CURVED) {
				x[n] = p.x;
				y[n] = p.y;
				if (debug) System.err.println("p.x=" + x[n] + " p.y=" + y[n] + " "
						+ p.curved);
				r.remove(0);
				n++;
			} else
				break;
		}
		if (n == 0)
			return false;
		// System.err.println("n=" + n);
		h = new float[n - 1];
		for (int i = 0; i < h.length; i++) {
			h[i] = x[i + 1] - x[i];
			// System.err.println("x[" + i + "]" + x[i] + " " + x[i + 1]);
		}
		n = h.length - 1; // n-2 if n points
		D = new float[n];
		lD = new float[n];
		uD = new float[n];
		v = new float[n];
		z = new float[n];
		for (int i = 0; i < n; i++) {
			D[i] = 2 * (h[i] + h[i + 1]);
			lD[i] = h[i + 1];
			uD[i] = h[i + 1];
			if (debug) System.err.println("y[" + (i + 2) + "]=" + y[i + 2] + " h["
					+ (i + 1) + "]=" + h[i + 1]);
			v[i] = 6 * ((y[i + 2] - y[i + 1]) / h[i + 1] - (y[i + 1] - y[i])
					/ h[i]);
//			System.err.println("v[" + i + "]=" + v[i] + " D[" + i + "]=" + D[i]
//					+ " " + lD[i] + " " + uD[i]);
		}
		return true;
	}

	static void solve(ArrayList<TypedPoint> r, boolean closed) {
		if (!computeMatrix(r))
			return;
		solveMatrix(lD, D, uD, v, z);
		int n = z.length;
		float[] S = new float[n + 2];
		for (int i = 0; i < n; i++) {
			S[i + 1] = z[i];
			// System.err.println("z[" + i + "]" + " z[i]" + z[i]);
		}
//		if (closed) {
//			S[0] = -S[n];
//			S[n + 1] = -S[1];
//		} else 
		{
			S[0] = 0;
			S[n + 1] = 0;
		}
		n = S.length;
		float[] a = new float[n - 1];
		float[] b = new float[n - 1];
		float[] c = new float[n - 1];
		float[] d = new float[n - 1];
		for (int i = 0; i < n - 1; i++) {
			a[i] = (S[i + 1] - S[i]) / (6 * h[i]) * (h[i] * h[i] * h[i]);
			b[i] = (S[i] / 2) * (h[i] * h[i]);
			c[i] = ((y[i + 1] - y[i]) / h[i] - (2 * h[i] * S[i] + h[i]
					* S[i + 1]) / 6)
					* h[i];
			d[i] = y[i];
			// System.err.println("i=" + i + " a=" + a[i] + " b=" + b[i] + " c="
			// + c[i] + " d=" + d[i] + "h=" + h[i]);
			if (debug) System.err.println("Check:" + (a[i] + b[i] + c[i] + d[i]));
		}
		n = a.length;
		P0 = new TypedPoint[n];
		P1 = new TypedPoint[n];
		P2 = new TypedPoint[n];
		P3 = new TypedPoint[n];
		for (int i = 0; i < n; i++) {
			P0[i] = new TypedPoint(x[i], d[i], TypedPoint.kind.NORMAL);
			// System.err.println("i=" + i + " " + P0[i].x + " " + P0[i].y);
			P1[i] = new TypedPoint(x[i] + h[i] / 3, c[i] / 3 + d[i], TypedPoint.kind.NORMAL);
			P2[i] = new TypedPoint(x[i] + 2 * h[i] / 3, b[i] / 3 + 2 * c[i] / 3
					+ d[i], TypedPoint.kind.NORMAL);
			P3[i] = new TypedPoint(x[i + 1], a[i] + b[i] + c[i] + d[i], TypedPoint.kind.NORMAL);
			// System.err.println("i=" + i + " " + P3[i].x + " " + P3[i].y);
		}
	}

}
