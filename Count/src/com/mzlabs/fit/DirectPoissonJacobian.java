package com.mzlabs.fit;


/**
 * Direct implementation of gradient and Hessian coefficient calculation
 * @author johnmount
 *
 */
public final class DirectPoissonJacobian implements BalanceJacobianCalc {
	private DirectPoissonJacobian() {
	}

	@Override
	public BalanceJacobianCoef calc(Obs obs, double[] beta) {
		final double bx = obs.dot(beta);
		final double balCoef = obs.wt*(obs.y-Math.exp(bx));  // hard coded Poisson gradient component
		final double jacCoef = obs.wt*(-Math.exp(bx));       // hard coded Poisson hessian component
		return new BalanceJacobianCoef(balCoef,jacCoef);
	}
	
	@Override
	public double evalEst(double[] beta, double[] x) {
		return Math.exp(Obs.dot(beta,x));
	}
	
	public static final DirectPoissonJacobian poissonGradHess = new DirectPoissonJacobian();
	public static GLMModel poissonLink = new GLMModel(poissonGradHess);
}
