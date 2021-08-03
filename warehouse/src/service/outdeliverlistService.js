import axios from 'axios'

export async function findAllOutdeliverlist() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/outdeliverlist/findAllOutdeliverlist')
    return result.data.data;
}

export async function addOutdeliverlist(outdeliverlist){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/outdeliverlist/addOutdeliverlist', outdeliverlist);
    return result.data;
}

export async function editOutdeliverlist(outdeliverlist){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/outdeliverlist/editOutdeliverlist',outdeliverlist)
    return result.data;
}

export async function deleteOutdeliverlist(odlId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/outdeliverlist/deleteOutdeliverlist/'+odlId);
    return result.data;
}

export async function updateOdlState(odlState,odlId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/outdeliverlist/updateOdlState',{
        params:{
            odlState,
            odlId
        }
    });
    return result.data;
}

export async function deleteStockAndStoragerack(srId,mtId,odlState,odlId,dispatchlist) {
    let result = await axios.post('http://localhost:8181/backEndWarehouse/outdeliverlist/deleteStockAndStoragerack',dispatchlist,{
        params:{
            srId,
            mtId,
            odlState,
            odlId,
        }
    })
    return result.data;
}

export async function reduceStock(odlId,odlState,stock,dpId,dpCreationTime,dpCreatorId) {
    let result = await axios.post('http://localhost:8181/backEndWarehouse/outdeliverlist/reduceStock',stock,{
        params:{
            odlState,
            odlId,
            dpId,
            dpCreationTime,
            dpCreatorId
        }
    })
    return result.data;
}

export async function getOdlByCtmId(odlCustomerId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/outdeliverlist/getOdlByCtmId',{
        params:{
            odlCustomerId,
        }
    });
    return result.data;
}