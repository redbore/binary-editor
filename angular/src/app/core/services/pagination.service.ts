import {Injectable} from "@angular/core";
import {PageNumber} from "../domain/PageNumber";

@Injectable()
export class PaginationService {
    readonly rowCounts: Array<number> = [10, 20, 50, 100, 200];
    selectedRowCount: number = this.rowCounts[0];
    maxRowCount: number = 1;

    readonly maxPageCount: number = 10;
    readonly maxNoCalculatePageNumber: number = this.maxPageCount / 2;

    pageNumbers: Array<PageNumber>;
    selectedPageNumber: number = 1;

    calculatePageNumbers() {
        let pageNumbers = new Array<PageNumber>();
        let pageCount = Math.ceil(this.maxRowCount / this.selectedRowCount);
        let maxPageCount = pageCount;
        let startPageNumber = 1;

        if (maxPageCount > this.maxPageCount) {

            maxPageCount = this.maxPageCount;
            if (this.selectedPageNumber <= this.maxNoCalculatePageNumber) {
                startPageNumber = 1;
            } else if (this.selectedPageNumber > pageCount - this.maxNoCalculatePageNumber) {
                startPageNumber = startPageNumber + pageCount - this.maxPageCount;
            } else {
                startPageNumber = startPageNumber + this.selectedPageNumber - this.maxNoCalculatePageNumber;
            }
        }
        let endPageNumber = startPageNumber + maxPageCount;
        for (let pageNumber = startPageNumber; pageNumber < endPageNumber; pageNumber++) {
            pageNumbers.push(new PageNumber(pageNumber, pageNumber == this.selectedPageNumber));
        }
        this.pageNumbers = pageNumbers;
    }

    goToStart() {
        this.selectedPageNumber = 1;
        this.calculatePageNumbers();
    }

    goToEnd() {
        this.selectedPageNumber = Math.ceil(this.maxRowCount / this.selectedRowCount);
        this.calculatePageNumbers();
    }

    selectPageNumber(selectedPageNumber: number) {
        this.selectedPageNumber = selectedPageNumber;
        this.calculatePageNumbers();
    }

    selectRowCount(selectedRowCount: number) {
        this.selectedRowCount = selectedRowCount;
        this.calculatePageNumbers();
    }
}
