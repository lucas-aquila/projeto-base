// ANGULAR
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask';
import { CurrencyMaskModule } from 'ng2-currency-mask';

// https://github.com/lentschi/ngx-ellipsis
import { EllipsisModule } from 'ngx-ellipsis';

// ANGULAR MATERIAL
import {
    DateAdapter,
    MAT_DATE_LOCALE,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatOptionModule,
    MatPaginatorModule,
    MatRadioModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatStepperModule,
    MatTableModule,
    MatToolbarModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatTabsModule
} from '@angular/material';

// COVALENT
import {
    CovalentChipsModule,
    CovalentCommonModule,
    CovalentFileModule,
    CovalentLoadingModule,
    CovalentMediaModule,
    CovalentDialogsModule,
    CovalentExpansionPanelModule
} from '@covalent/core';
import { CovalentMarkdownModule } from '@covalent/markdown';

/**
 *
 */
@NgModule({
    imports: [
        // ANGULAR
        CommonModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule, 
        TextMaskModule,
        CurrencyMaskModule,

        // ANGULAR MATERIAL
        MatAutocompleteModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatChipsModule,
        MatDatepickerModule,
        MatDialogModule,
        MatGridListModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatMenuModule,
        MatNativeDateModule,
        MatOptionModule,
        MatPaginatorModule,
        MatRadioModule,
        MatSelectModule,
        MatSidenavModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatSnackBarModule,
        MatSortModule,
        MatStepperModule,
        MatTableModule,
        MatToolbarModule,
        MatTooltipModule,
        MatFormFieldModule,
        MatTabsModule,

        // COVALENT
        CovalentChipsModule,
        CovalentCommonModule,
        CovalentFileModule,
        CovalentLoadingModule,
        CovalentMediaModule,
        CovalentDialogsModule,
        CovalentMarkdownModule,
        CovalentExpansionPanelModule,

        // Ellipsis
        EllipsisModule
    ],
    exports: [
        // ANGULAR
        CommonModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule, 
        TextMaskModule,
        CurrencyMaskModule,

        // ANGULAR MATERIAL
        MatAutocompleteModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatChipsModule,
        MatDatepickerModule,
        MatDialogModule,
        MatGridListModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatMenuModule,
        MatNativeDateModule,
        MatOptionModule,
        MatPaginatorModule,
        MatRadioModule,
        MatSelectModule,
        MatSidenavModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatSnackBarModule,
        MatSortModule,
        MatStepperModule,
        MatTableModule,
        MatToolbarModule,
        MatTooltipModule,
        MatFormFieldModule,
        MatTabsModule,

        // COVALENT
        CovalentChipsModule,
        CovalentCommonModule,
        CovalentFileModule,
        CovalentLoadingModule,
        CovalentMediaModule,
        CovalentDialogsModule,
        CovalentMarkdownModule,
        CovalentExpansionPanelModule,

        // Ellipsis
        EllipsisModule
    ],
    providers: [
        // The locale would typically be provided on the root module of your application. We do it at
        // the component level here, due to limitations of our example generation script.
        { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }
    ]
})
export class SharedModule {
    constructor(public dateAdapter: DateAdapter<Date>) {
        dateAdapter.setLocale('pt-BR');
    }
}
