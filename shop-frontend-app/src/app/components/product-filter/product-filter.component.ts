import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {ProductFilterDto} from "../../model/product-filter";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  @Output() filterApplied = new EventEmitter<ProductFilterDto>();

  productFilter: ProductFilterDto = new ProductFilterDto("", 0, 0);

  constructor() { }

  ngOnInit(): void {
  }

  applyFilter() {
    this.filterApplied.emit(this.productFilter);
  }
}
