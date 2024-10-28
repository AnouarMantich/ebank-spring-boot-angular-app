export interface AccountDetails {
  accountId: string;
  balance: number;
  currentPage: number;
  totalPages: number;
  pageSize: number;
  accountOperationDTOS:AccountOperation[];
}

export interface AccountOperation {
  id:number;
  operationTimestamp : string;
  amount :number;
  type :OperationType;
  description :String;
}

enum OperationType {
  DEBIT ,CREDIT
}
