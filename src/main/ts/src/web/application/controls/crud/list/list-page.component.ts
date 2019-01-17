import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog } from '@angular/material';
import { CrudViewComponent } from '../crud-view.component';
import { debounce } from '../../../../domain/utils/debounce';
import { getLocalStorage, setLocalStorage } from '../../../../domain/utils/handle-local-storage';

@Component({
    selector: 'list-page',
    templateUrl: 'list-page.component.html'
})
export class ListPageComponent extends CrudViewComponent implements OnInit {
    
    @ViewChild(MatPaginator) paginator: MatPaginator; // Bind com o objeto paginator
    @ViewChild(MatSort) sort: MatSort; // Bind com objeto sort

    public filters: any = { text: '', status: '' }; // Estado inicial dos filtros    

    public debounce = debounce;
    public listByFiltersStatement = () => this.listByFilters(true);

    // Tabela
    @Input() dataSource: any;
    @Input() columns: any;
    @Input() displayedColumns: any;
    @Input() totalElements: any;

    // Emite um evento de acordo com a função passada para o mesmo
    @Output() list = new EventEmitter();
    @Output() delete = new EventEmitter();

    /**
     * 
     */
    ngOnInit() {
        this.handleLabelStatus();

        // Verifica e mantém o estado dos filtros
        this.filters = getLocalStorage( this.filters, this.activatedRoute.component['name'] );
        
        // Listagem inicial
        this.listByFilters();
    }

    /**
     * Restaura os filtros para o estado inicial
     */
    clearFilters = () => {
        const { text, status } = this.filters;

        if ( text || status !== '' ) {
            this.filters = { text: '', status: '' };
            this.listByFilters();
        }
    };

    /**
     * Emite um evento para chamar a função no componente que o está invocando
     */
    listByFilters = ( hasAnyFilter: boolean = false ) => {
        setLocalStorage( this.filters, this.activatedRoute.component['name'] );
        this.list.emit( hasAnyFilter );
    };

    openDeleteDialog = (data) => this.delete.emit(data);
}
