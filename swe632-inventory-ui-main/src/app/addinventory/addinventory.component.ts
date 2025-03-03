import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // <-- Import CommonModule
import { MemoryStorageService } from '../memory-storage.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import {Item} from "../item";

@Component({
	// standalone: true,
	selector: 'app-addinventory',
	imports: [FormsModule, CommonModule, MatSnackBarModule], // <-- Add CommonModule here
	template: `
	<div class="addinventory-container">
		<h2>Local Inventory</h2>

		<ul>
			<li *ngFor="let item of items; let i = index">
				{{ item.name }} (Qty: {{ item.quantity }})
				<button (click)="deleteItem(i)">Delete</button>
			</li>
		</ul>

		<!-- <button mat-button (click)="openDialog()">Open Pop-up</button> -->
		<h3>Add New Product</h3>
		<form #myForm="ngForm" (ngSubmit)="addItem()">
			<input type="text" name="itemName" [(ngModel)]="newItem.name" placeholder="Item Name" required
				   #myFieldModel="ngModel">
			<input type="number" name="itemQuantity" [(ngModel)]="newItem.quantity" min="1" required>
			<!-- <input type="number" [(ngModel)]="newItem.quantity" [ngModelOptions]="{standalone: true}" min="1" required> -->
			<button mat-raised-button type="submit" [disabled]="myForm.invalid">Add</button>
		</form>
	</div>
	`,
	standalone: true,
	styleUrls: ['./addinventory.component.css']  // Note: Changed to styleUrls
})
export class AddinventoryComponent {
	newItem = new Item('', 1);
	items: any[] = [];

	constructor(private memoryStorage: MemoryStorageService, private snackbar: MatSnackBar) {}

	ngOnInit() {
		this.memoryStorage.items$.subscribe(data => {
			console.log('Items updated:', data); // Debug log
			this.items = data;
		});
	}

	addItem() {
		console.log('addItem called with:', this.newItem);
		this.snackbar.open("Item added successfully!", "Close", {
			duration: 3000,
		});
		this.memoryStorage.addItem(this.newItem);
		this.newItem = new Item('', 1);
	}

	deleteItem(index: number) {
		this.memoryStorage.deleteItem(index);
	}
	ngAfterViewInit() {
		const elements = document.getElementsByClassName("active");
		const elementsArray = Array.from(elements);
		elementsArray.forEach((element) => {
			element.classList.remove("active");
		});
		const this_element = document.getElementById("add_button") as HTMLElement;
		this_element.classList.add("active");
	}
	
}
