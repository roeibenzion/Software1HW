package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {
	

	private double [] polynomial;
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial()
	{
	 	this.polynomial = new double [1];
	} 
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		int deg = checkNewDegree(coefficients);
		this.polynomial = new double[deg+1];
		for (int i = 0; i < deg+1; i++) {
			polynomial[i] = coefficients[i];
		}
	}
	private int checkNewDegree(double[] coefficients)
	{
		int deg = 0;
		for (int i = coefficients.length-1; i >= 0; i--) {
			if(coefficients[i] != 0)
			{
				deg = i;
				break;
			}
		}
		return deg;
	}
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		int i = 0, deg = Math.max(this.getDegree(), polynomial.getDegree());
		double [] nCoefficients = new double[deg+1];
		
		while(i <= this.getDegree() && i <= polynomial.getDegree())
		{
			nCoefficients[i] = this.polynomial[i] + polynomial.polynomial[i];
			i++;
		}
		
		while(i <= this.getDegree())
		{
			nCoefficients[i] = this.polynomial[i];
			i++;
		}
		
		while(i <= polynomial.getDegree())
		{
			nCoefficients[i] = polynomial.polynomial[i];
			i++;
		}
		
		Polynomial retPolynomial = new Polynomial(nCoefficients);
		return retPolynomial;
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		if(a == 0)
			return new Polynomial();
		
		double[] nCoefficients = new double[this.getDegree()+1];
		for (int i = 0; i < nCoefficients.length; i++) {
			nCoefficients[i] = a*this.polynomial[i];
		}
		Polynomial retPolynomial = new Polynomial(nCoefficients);
		return retPolynomial;
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		return this.polynomial.length-1;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		if(n < 0 || n > this.getDegree())
			return 0.0;
		
		return this.polynomial[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		if(n < 0)
			return;
		
		if(n < this.getDegree())
			this.polynomial[n] = c;
		else
		{
			double [] nCoefficients = new double[n+1];
			nCoefficients[n] = c;
			for (int i = 0; i < this.polynomial.length; i++) {
				if(i == n)
					continue;
				nCoefficients[i] = polynomial[i];
			}
			nCoefficients = Arrays.copyOf(nCoefficients,checkNewDegree(nCoefficients)+1);
			this.polynomial = new double[nCoefficients.length];
			this.polynomial = Arrays.copyOf(nCoefficients, nCoefficients.length);
		}
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		Polynomial derivative;
		
		if(this.getDegree() == 0)
			derivative = new Polynomial();
		else 
		{
			double[] nCoefficiants = new double [this.getDegree()];
			for (int i = 1, j = 0; j < nCoefficiants.length; i++, j++) {
				nCoefficiants[j] = this.polynomial[i]*i;
			}
			derivative = new Polynomial(nCoefficiants);
		}
		return derivative;
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double value = 0.0;
		for (int i = 0; i <= this.getDegree(); i++) {
			value += this.polynomial[i] * Math.pow(x, i);
		}
		return value;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		Polynomial firstDerivative = this.getFirstDerivation();
		Polynomial secondDerivative = firstDerivative.getFirstDerivation();
		return (firstDerivative.computePolynomial(x)) == 0 && !(secondDerivative.computePolynomial(x) == 0);
	}

}
