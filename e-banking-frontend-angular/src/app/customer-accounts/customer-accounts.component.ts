import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AccountService} from "../services/account.service";
import {CustomerAccounts} from "../models/customer-accounts";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrl: './customer-accounts.component.css'
})
export class CustomerAccountsComponent implements OnInit {
  public customerAccounts!:CustomerAccounts;

  constructor(private activatedRoute: ActivatedRoute,private accountService: AccountService,public authService: AuthService) {
  }
    ngOnInit(): void {
        let customerId = this.activatedRoute.snapshot.params['id'];
        this.accountService.customerAccounts(customerId).subscribe({
          next: results => {
            this.customerAccounts= results;
          }
        })
    }



}
