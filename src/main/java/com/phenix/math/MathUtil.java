package com.phenix.math;

import com.phenix.util.Util;
import mosek.fusion.*;
import org.apache.commons.math3.analysis.function.Max;
import org.apache.commons.math3.analysis.function.Min;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.util.Arrays;

public final class MathUtil {
    private final static Mean MEAN = new Mean();
    private final static StandardDeviation STD = new StandardDeviation();
    private final static Percentile PERCENTILE = new Percentile();

    public static double[] l1filter(double[] y, double lambda) throws SolutionError {
        int n = y.length;
        double diffMatrix[][] = getDiffMatrix(n, 2).getData();
        try (Model M = new Model("l1fitler")) {

            Variable x = M.variable("x", n, Domain.unbounded());
            Variable t = M.variable("t", 1, Domain.unbounded());
            Variable z = M.variable("z", n - 2, Domain.unbounded());
            Variable w = M.variable("w", n - 2, Domain.unbounded());
            Matrix D = Matrix.dense(diffMatrix);

            M.constraint("c1", Expr.vstack(0.5, Expr.vstack(t, Expr.sub(y, x))), Domain.inRotatedQCone());
            M.constraint("c2", Expr.hstack(z, w), Domain.inQCone());
            M.constraint("c3", Expr.sub(w, Expr.mul(D, x)), Domain.equalsTo(0));

            M.objective("obj", ObjectiveSense.Minimize, Expr.add(Expr.mul(0.5, t), Expr.mul(lambda, Expr.sum(z))));
            M.solve();


            double[] sol = x.level();
            return sol;
        }
    }

    //TODO: parameter checking, relation with dimension and order
    public static RealMatrix getDiffMatrix(int dimension, int order) {
        if (order == 1) {
            RealMatrix diffMatrix = MatrixUtils.createRealMatrix(dimension - 1, dimension);
            for (int i = 0; i <= dimension - 2; i++) {
                diffMatrix.setEntry(i, i, 1);
                diffMatrix.setEntry(i, i + 1, -1);
            }
            return diffMatrix;
        } else {
            return getDiffMatrix(dimension - order + 1, 1).multiply(getDiffMatrix(dimension, order - 1));
        }
    }

    //equivalent of matlab diff
    public static double[] diff(double []d_, int order) {
        if(order == 1) {
           return _diff1(d_);
        } else {
            double []d = _diff1(d_);
            return diff(d, order -1);
        }
    }
    private static double[] _diff1(double []d_) {
        double []r = new double[d_.length - 1];
        for(int i = 0; i < r.length; i++) {
            r[i] = d_[i + 1] - d_[i];
        }
        return r;
    }

    public static double computeDtw(double []v1_, double[]v2_) {
        double [][]d = new double[v1_.length][v2_.length];
        double [][]distance = new double[v1_.length][v2_.length];
        for(int i = 0; i < v1_.length; i++) {
            for(int j = 0; j < v2_.length; j++) {
                d[i][j] = Math.abs(v1_[i] - v2_[j]);
            }
        }

        double d1, d2, d3;
        for(int i = 0; i < v1_.length; i++) {
            for(int j = 0; j < v2_.length; j++) {
                if(i == 0 && j == 0) {
                    distance[i][j] = d[i][j];
                    continue;
                } else if(i == 0) {
                    d1 = Double.MAX_VALUE;
                    d2 = distance[i][j - 1];
                    d3 = Double.MAX_VALUE;
                } else if(j == 0) {
                    d1 = distance[i - 1][j];
                    d2 = Double.MAX_VALUE;
                    d3 = Double.MAX_VALUE;
                } else {
                    d1 = distance[i-1][j];
                    d2 = distance[i][j-1];
                    d3 = distance[i-1][j-1];
                }
                distance[i][j] = d[i][j] + Double.min(d1, Double.min(d2, d3));
            }
        }

        return distance[v1_.length - 1][v2_.length - 1];
    }

    public static double[] normalize(double []d_) {
        double m = MEAN.evaluate(d_);
        double d = STD.evaluate(d_);
        return Arrays.stream(d_).map(e -> (e - m) / d).toArray();
    }

    public static double std(double []d_) {
        return STD.evaluate(d_);
    }

    public static double mean(double []d_) {
        return MEAN.evaluate(d_);
    }

    public static double min(double []d_) {
        double res = Double.POSITIVE_INFINITY;
        for(double d : d_) {
            res = Math.min(res, d);
        }
        return res;
    }

    public static double max(double []d_) {
        double res = Double.NEGATIVE_INFINITY;
        for(double d : d_) {
            res = Math.max(res, d);
        }
        return res;
    }

    public static double quantile(double []d, double pivot_) {
        return PERCENTILE.evaluate(d, pivot_);
    }
}
