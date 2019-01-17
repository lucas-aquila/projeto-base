import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { UsuarioService } from '../../../../../services/usuario.service';
import { HomeViewComponent } from '../../../home-view.component';
import { DialogService } from '../../../../../services/dialog.service';
import { MessageService } from '../../../../../services/message.service';
import { PaginationService } from '../../../../../services/pagination.service';
import { handlePageable } from '../../../../../utils/handle-data-table';
import { debounce } from '../../../../../utils/debounce';
import { getLocalStorage, setLocalStorage } from '../../../../../utils/handle-local-storage';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'consultar-usuarios',
    templateUrl: './consultar-usuarios.component.html',
    styleUrls: ['../usuario.component.scss']
})
export class ConsultarUsuariosComponent implements OnInit {
    
    // Bind com o objeto paginator
    @ViewChild(MatPaginator) paginator: MatPaginator;
    
    // Bind com objeto sort
    @ViewChild(MatSort) sort: MatSort;

    public pageable: any;
    public totalElements: any;

    public debounce = debounce;
    public listByFiltersStatement = () => this.listByFilters(true);

    // Estado inicial dos filtros
    public filters: any = { text: '', status: '' };

    public columns: any[] = [
        { name: 'nome', label: 'Nome' },
        { name: 'email', label: 'E-mail' }
    ];

    public displayedColumns: string[] = this.columns.map(cell => cell.name);

    public dataSource = new MatTableDataSource();

    /**
     * 
     * @param usuarioService 
     * @param homeView 
     * @param dialogService 
     * @param messageService 
     * @param paginationService 
     * @param activatedRoute 
     */
    constructor(
        public usuarioService: UsuarioService, 
        public homeView: HomeViewComponent,
        public dialogService: DialogService,
        public messageService: MessageService,
        public paginationService: PaginationService,
        public activatedRoute: ActivatedRoute
    ) {
        homeView.toolbar.headline = 'Cadastros';
        homeView.toolbar.subhead = 'Usuário';
        this.displayedColumns.push('organizacao', 'status', 'acoes');
        this.pageable = paginationService.pageable('nome');
    }

    /**
     *
     */
    ngOnInit() {
        // Seta o size do pageable no size do paginator
        this.paginator.pageSize = this.pageable.size;

        // Verifica e mantém o estado dos filtros
        this.filters = getLocalStorage( this.filters, this.activatedRoute.component['name'] );
        
        // Listagem inicial
        this.listByFilters();

        // Sobrescreve o sortChange do sort bindado
        this.sortChange();
    }

    /**
     * 
     */
    public sortChange() {    
        this.sort.sortChange.subscribe(() => {
            const { active, direction } = this.sort;
            this.pageable.sort = { 'properties': active, 'direction': direction };            
            this.listByFilters();
        });
    }

    /**
     * 
     * @param hasAnyFilter Verifica se há algum filtro,
     * caso exista, então será redirecionado para a primeira página
     */
    public listByFilters( hasAnyFilter: boolean = false ) {

        // Define o estado atual dos filtros
        setLocalStorage( this.filters, this.activatedRoute.component['name'] );

        const pageable = handlePageable( hasAnyFilter, this.paginator, this.pageable );
        const { text, status } = this.filters;

        this.usuarioService.listUsuariosByFilters(text, status, pageable)
        .then(result => {
            this.dataSource = new MatTableDataSource(result.content);
            this.totalElements = result.totalElements;
        });
    }

    /**
     * Restaura os filtros para o estado inicial
     */
    public clearFilters() {
        const { text, status } = this.filters;

        if ( text || status !== '' ) {
            this.filters = { text: '', status: '' };
            this.listByFilters();
        }
    };
}
