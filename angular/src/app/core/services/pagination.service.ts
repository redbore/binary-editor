import {Injectable} from "@angular/core";

@Injectable()
export class PaginationService {

    viewRowCounts: Array<number> = [5, 10, 20, 50, 100, 200]; // migrate to json
    selectedViewRowCount: number = this.viewRowCounts[0];
    pageNumber: number = 1;
    tableRowCount: number = 1;

    pageNumbers: Array<number>;

    calculatePageNumbers() {
        let pageCount = this.tableRowCount / this.selectedViewRowCount;
        if (pageCount < 1) {
            pageCount = 1;
        }
        if (pageCount > 10) {
            pageCount = 10
        }

        let array = new Array<number>(pageCount);
        for (let i = this.pageNumber, j = 0; i < this.pageNumber + pageCount; i++, j++) {
            array[j] = i;
        }
        this.pageNumbers = array;
    }

    setPageNumber(pageNumber: number) {
        this.pageNumber = pageNumber;
        this.calculatePageNumbers();
    }

    setViewRowCount(viewRowCount: number) {
        this.selectedViewRowCount = viewRowCount;
        this.calculatePageNumbers();
    }
}
