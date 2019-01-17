import { Component, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MessageService } from '../../../domain/services/message.service';

@Component({
    selector: 'delete-dialog',
    templateUrl: 'delete-dialog.component.html',
    styleUrls: ['./delete-dialog.component.scss']
})
export class DeleteDialogComponent {
    /**
     * 
     * @param dialogRef 
     * @param data 
     */
    constructor(
        public dialogRef: MatDialogRef<DeleteDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {}
}
