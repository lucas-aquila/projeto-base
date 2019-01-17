import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

// Para diferenciar se a entidade é uma palavra masculina ou feminina
export type Gender = 'M' | 'F';

@Component({ selector: 'crud-view', template: '' })
export class CrudViewComponent {

    public labelStatus: any = {}; // Armazena o texto que será exibido de acordo com o gênero
        
    /**
     * Cabeçalho
     */
    @Input() headline: string = 'Cadastros'; // Armazena o título padrão da página
    @Input() subhead: string; // Armazena o subtítulo padrão da página
    @Input() gender: Gender; // Armazena o gênero da entidade para tratar as labels conforme o mesmo
    
    /**
     * Utilizado apenas nos componentes de FORM e DETAIL
     */
    @Input() entity: any = {}; // Recebe a entidade corrente
    @Input() hasDescription: boolean = false; // Verifica se a entidade possui o campo descricao

    /**
     * 
     * @param activatedRoute 
     */
    constructor(public activatedRoute: ActivatedRoute) {}

    /**
     * Manipula as labels do Status de acordo com o gênero informado
     */
    handleLabelStatus = () => {
        if ( this.gender === 'M' ) {
            this.labelStatus = { all: 'Todos', active: 'Ativo', inactive: 'Inativo' };
        } else {
            this.labelStatus = { all: 'Todas', active: 'Ativa', inactive: 'Inativa' };
        }
    }
}
