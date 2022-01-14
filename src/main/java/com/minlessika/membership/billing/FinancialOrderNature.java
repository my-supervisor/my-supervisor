package com.minlessika.membership.billing;

public enum FinancialOrderNature {
	NONE    {
		@Override
		public String toString() {
            return "Undefined";
        }
	},
	DEPOSIT  {
		@Override
		public String toString() {
            return "Deposit";
        }
	}, 
	WITHDRAWAL  {
		@Override
		public String toString() {
            return "Withdrawal";
        }
	};
}
