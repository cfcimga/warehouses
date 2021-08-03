import axios from 'axios'

export async function getAllStock() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/stock/findAllStock')
    return result.data.data;
}


export async function addStock(stock){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/stock/addStock', stock);
    return result.data;
}

export async function editStock(stock){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/stock/editStock',stock)
    return result.data;
}

export async function deleteStock(srId,mtId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/stock/deleteStock',{
        params:{
            srId,
            mtId
        }
    });
    return result.data;
}

export async function getStockBySrId(srId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/stock/getStockBySrId',{
        params:{
            srId,
        }
    })
    return result.data;
}
export async function getStockByMtId(mtId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/stock/getStockByMtId',{
        params:{
            mtId,
        }
    })
    return result.data;
}



