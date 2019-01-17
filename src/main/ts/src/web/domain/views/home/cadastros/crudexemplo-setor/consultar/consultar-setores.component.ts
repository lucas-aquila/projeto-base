import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { DialogService } from '../../../../../services/dialog.service';
import { MessageService } from '../../../../../services/message.service';
import { PaginationService } from '../../../../../services/pagination.service';
import { SetorService } from '../../../../../services/setor.service';
import { ListPageComponent } from '../../../../../../application/controls/crud/list/list-page.component';
import { handlePageable } from '../../../../../utils/handle-data-table';

@Component({
    selector: 'consultar-setores',
    templateUrl: './consultar-setores.component.html',
    styleUrls: ['../setor.component.scss']
})
export class ConsultarSetoresComponent implements OnInit {
    
    // Bind com o component ListPageComponent
    @ViewChild(ListPageComponent) 
    private setor: any = {};

    public pageable: any;
    public totalElements: any;

    public columns: any[] = [
        { name: 'nome', label: 'Nome' },
        { name: 'sigla', label: 'Sigla' }
    ];

    public displayedColumns: string[] = this.columns.map(cell => cell.name);

    public dataSource = new MatTableDataSource();

    /**
     * 
     * @param setorService 
     * @param dialogService 
     * @param messageService 
     * @param paginationService 
     */
    constructor(
        public setorService: SetorService,
        public dialogService: DialogService,
        public messageService: MessageService,
        public paginationService: PaginationService
    ) {
        this.displayedColumns.push('status', 'acoes');
        this.pageable = paginationService.pageable('nome');
    }

    /**
     *
     */
    ngOnInit() {
        // Seta o size do pageable no size do paginator
        this.setor.paginator.pageSize = this.pageable.size;

        // Sobrescreve o sortChange do sort bindado
        this.sortChange();        
    }

    /**
     * 
     */
    public sortChange() {    
        this.setor.sort.sortChange.subscribe(() => {
            const { active, direction } = this.setor.sort;
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

        const pageable = handlePageable( hasAnyFilter, this.setor.paginator, this.pageable );
        const { text, status } = this.setor.filters;

        this.setorService.listSetoresByFilters(text, status, pageable)
        .then(result => {
            this.dataSource = new MatTableDataSource(result.content);
            this.totalElements = result.totalElements;
        });
    }

    /**
     * Função para confirmar a exclusão de um registro permanentemente
     * @param setor 
     */
    public openDeleteDialog( setor ) {

        this.dialogService.confirmDelete(setor, 'setor')
        .then((accept: boolean) => {

            if ( accept ) {
                this.setorService.deleteSetor( setor.id )
                .then(() => {
                    this.listByFilters();
                    this.messageService.toastSuccess('Registro excluído com sucesso.')
                });
            }
        });
    }
}
