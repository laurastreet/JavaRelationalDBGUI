import { Component, OnInit, AfterViewInit } from '@angular/core';
import { MemoryStorageService } from '../memory-storage.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-lowstockalert',
  imports: [CommonModule],
  templateUrl: './lowstockalert.component.html',
  styleUrls: ['./lowstockalert.component.css']
})
export class LowstockalertComponent implements OnInit, AfterViewInit {
  items: any[] = [];
  stock_limit: number = 5;

  constructor(private memoryStorage: MemoryStorageService) {}

  ngOnInit(): void {
    this.memoryStorage.items$.subscribe((data: any[]) => {
      this.items = data;
      console.log('update inventory items:', this.items);
    });
  }

  ngAfterViewInit(): void {
    const elements = document.getElementsByClassName("active");
    const elementsArray = Array.from(elements);
    elementsArray.forEach((element) => {
      element.classList.remove("active");
    });
    const thisElement = document.getElementById("lowstock_button") as HTMLElement;
    if (thisElement) {
      thisElement.classList.add("active");
    }
  }

  isLowStock(item: any): boolean {
    return item.quantity < this.stock_limit;
  }

  isInventoryEmptyOrStocked(): boolean {
    if (this.items.length <= 0) {
      return true;
    } else {
      for (let item of this.items) {
        if (item.quantity < this.stock_limit) {
          return false;
        }
      }
    }
    return true;
  }
}
