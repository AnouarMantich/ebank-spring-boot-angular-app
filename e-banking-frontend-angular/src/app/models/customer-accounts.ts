import {Customer} from "./Customer";

export interface CustomerAccounts {
  customer:Customer;
  bankAccounts:BankAccount[]

}

interface BankAccount {
  id: string,
  balance: number,
  createdAt: string,
  status: string,
  type: OperationType,
  overdraft: number,
  interestRate: number,
}


enum OperationType {
  DEBIT,CREDIT
}
