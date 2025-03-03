import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemoryStorageService } from '../memory-storage.service';
import { MatDialog, MatDialogModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { DialogContentComponent } from '../dialog-content/dialog-content.component';

@Component({
	selector: 'app-delinventory',
	imports: [CommonModule, MatButtonModule],
	template: `
	<div class="delinventory-container">
		<h2>Delete Inventory</h2>
		<ul>
			<li *ngFor="let item of items; let i = index">
				{{ item.name }} (Qty: {{ item.quantity }})
				<!-- <button (click)="deleteItem(i)">Delete</button> -->
				<button mat-button (click)="openDialog(i)">Delete</button>
			</li>
		</ul>
	</div>
	`,
	styleUrls: ['./delinventory.component.css'],
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class DelinventoryComponent {

	items: any[] = [];

	readonly dialog = inject(MatDialog);
	
	constructor(private memoryStorage: MemoryStorageService, private cdr: ChangeDetectorRef) {}

	ngOnInit() {
		this.memoryStorage.items$.subscribe(data => {
			this.items = data;
			console.log('Delinventory items:', this.items);
		});
	}

	openDialog(index: number) {
		const dialogRef = this.dialog.open(DialogContentExampleDialog);
	
		dialogRef.afterClosed().subscribe(result => {
		  console.log(`Dialog result: ${result}`);
		  if (result) {
			this.deleteItem(index);
		  }
		});
	  }

	deleteItem(index: number) {
		
		this.memoryStorage.deleteItem(index);
		this.cdr.markForCheck();
	}
	ngAfterViewInit() {
		const elements = document.getElementsByClassName("active");
		const elementsArray = Array.from(elements);
		elementsArray.forEach((element) => {
			element.classList.remove("active");
		});
		const this_element = document.getElementById("del_button") as HTMLElement;
		this_element.classList.add("active");
	}
}


@Component({
	selector: 'dialog-animations-example-dialog',
	templateUrl: 'delinventory.component.html',
	imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle],
	changeDetection: ChangeDetectionStrategy.OnPush,
  })
export class DialogContentExampleDialog {}
