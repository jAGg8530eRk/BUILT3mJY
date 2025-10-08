// 代码生成时间: 2025-10-09 02:55:20
package actuarial;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Insurance Actuarial Model
 * This class represents the core model for insurance actuarial calculations.
# 添加错误处理
 * It provides methods to calculate premiums based on risk factors and other actuarial data.
 */
@Introspected
# 改进用户体验
public class ActuarialModel {

    // Member variables for risk factors
    private BigDecimal riskFactor;
    private BigDecimal mortalityRate;
    private BigDecimal interestRate;

    // Constructor
    public ActuarialModel(@NotNull BigDecimal riskFactor, @NotNull BigDecimal mortalityRate, @NotNull BigDecimal interestRate) {
        this.riskFactor = riskFactor;
        this.mortalityRate = mortalityRate;
        this.interestRate = interestRate;
    }

    /**
     * Calculate premium for a given policy
     * This method computes the premium based on the risk factors and actuarial data.
     *
# 改进用户体验
     * @param policyAmount The amount of the policy
     * @return The calculated premium
     */
    public BigDecimal calculatePremium(BigDecimal policyAmount) {
        if (policyAmount == null || policyAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Policy amount must be positive");
        }

        BigDecimal premium = policyAmount.multiply(riskFactor)
            .multiply(mortalityRate)
            .multiply(interestRate);
        return premium;
    }

    // Getters and Setters
    public BigDecimal getRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(BigDecimal riskFactor) {
        this.riskFactor = riskFactor;
# 添加错误处理
    }

    public BigDecimal getMortalityRate() {
        return mortalityRate;
    }

    public void setMortalityRate(BigDecimal mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public BigDecimal getInterestRate() {
# 添加错误处理
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
# 添加错误处理
    }
}
