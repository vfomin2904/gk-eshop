import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {Order} from "../../model/order";
import {OrderStatusService} from "../../services/order-status.service";

export const ORDERS_URL = 'order';

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.scss']
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService,
              private orderStatusService: OrderStatusService) {
  }

  ngOnInit(): void {
    this.orderService.findOrdersByCurrentUser()
      .subscribe(orders => {
          this.orders = orders;
        },
        error => {
          console.log(error);
        });
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => {
        console.log(`New message with status ${msg.state}`);
        let updated = this.orders.find(order => order.id === msg.id);
        if (updated) {
          updated.status = msg.state;
        }
      });
  }

}
