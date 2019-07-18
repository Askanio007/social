import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';

interface PaginationProps {
    countRecord:number
    currentPage: number
    handlePage:any
}
export class Pagination extends Component<PaginationProps, any> {

    getCountPage = () => {
        let countPage = this.props.countRecord / 10;
        return Math.ceil(countPage);
    };

    render() {
        let countPage = this.getCountPage();

        if (countPage < 2) {
            return null;
        }
        let records = [];
        let currentPage = this.props.currentPage + 1;

        if (currentPage <= 5) {
            for (let i = 0; i < (countPage < 10 ? countPage : 10); i++) {
                records.push(i);
            }
        } else if (currentPage > 5 && currentPage < countPage - 5) {
            for (let i = currentPage - 5; i < currentPage + 5 ; i++) {
                records.push(i);
            }
        } else {
            for (let i = countPage - 10; i < countPage ; i++) {
                records.push(i);
            }
        }

        let item = records.map((value:any, index:number) =>
            <li className="page-item" key={index} onClick={() => this.props.handlePage(index)}><a className="page-link">{value + 1}</a></li>
        );
        return (
            <div className="d-flex">
            <ul className="pagination mx-auto">
                <li className="page-item" onClick={() => this.props.handlePage(0)}><a className="page-link"><FormattedMessage id="common.pagination.first" /></a></li>
                {item}
                <li className="page-item" onClick={() => this.props.handlePage(countPage)}><a className="page-link"><FormattedMessage id="common.pagination.last" /></a></li>
            </ul>
            </div>
        );
    }
}
