import * as React from "react";
import {getProfit} from "./api";

interface State {
    amount?: number;
    historicalDate?: string;
    result?: number;
    error?: string;
    isCalculation?: boolean;
}

class Exchanger extends React.Component<{}, State> {

    state: State = {
        amount: 0.00,
        historicalDate: "",
        result: 0.00,
        error: null,
        isCalculation: false
    };

    handleCalculate = () => {
        this.setState({
            error: null,
            result: 0.00,
            isCalculation: true
        });

        fetch(getProfit + "?amount=" + this.state.amount + "&historicalDate=" + this.state.historicalDate)
            .then(response => response.json())
            .then(data => {
                this.setState({isCalculation: false});
                data.success === true ? this.setState({result: data.data}) : this.setState({error: data.error});
            });
    };

    handleChange = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            [event.currentTarget.name]: event.currentTarget.value
        });
    };

    render() {
        let error = this.state.error !== null ? <div className="alert alert-danger" role="alert">{this.state.error}</div> : "";
        let loading = this.state.isCalculation ? <div className="alert alert-primary" role="alert">Calculation...</div> : "";
        return (
            <div className="container-fluid">
                <h2>Currency exchange</h2>
                <div className="row justify-content-start">
                    <div className="col-2">
                        <div>
                            <label htmlFor="amountInput">Enter amount (EUR):</label>
                            <input id="amountInput" name="amount" value={this.state.amount} className="form-control"
                                   placeholder="EUR" onChange={this.handleChange}/>
                        </div>
                        <div>
                            <label htmlFor="dateInput">Enter historical date:</label>
                            <input id="dateInput" type="date" name="historicalDate" value={this.state.historicalDate}
                                   className="form-control" placeholder="Historical date" onChange={this.handleChange}/>
                        </div>
                        <br />
                    </div>
                    <div className="col-2">
                        <div>
                            <label htmlFor="resultInput">Result (RUB): </label>
                            <input readOnly id="resultInput" name="result" value={this.state.result}
                                   className="form-control" placeholder="RUB"/>
                        </div>
                    </div>
                </div>
                <div className="row justify-content-start">
                    <div className="col-4">
                        <div>
                            {error}{loading}
                        </div>
                    </div>
                </div>
                <div className="row justify-content-start">
                    <div className="col-4">
                        <div>
                            <button className="btn btn-primary" onClick={this.handleCalculate}>Recalculate</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Exchanger;