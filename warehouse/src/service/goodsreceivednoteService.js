import axios from 'axios'

export async function findAllGoodsReceivedNote() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/goodsreceivednote/findAllGoodsReceivedNote')
    return result.data.data;
}

export async function addGoodsReceivedNote(goodsreceivednote,dpId){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/goodsreceivednote/addGoodsReceivedNote', goodsreceivednote,{
        params:{
            dpId
        }
    });
    return result.data;
}

export async function editGoodsReceivedNote(goodsreceivednote,dpId,dpState){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/goodsreceivednote/editGoodsReceivedNote',goodsreceivednote,{
        params:{
            dpId,
            dpState
        }
    })
    return result.data;
}

export async function deleteGoodsReceivedNote(grnId,dpId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/goodsreceivednote/deleteGoodsReceivedNote',{
        params:{
            grnId,
            dpId
        }
    });
    return result.data;
}

export async function updateState(grnState,grnId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/goodsreceivednote/updateState',{
        params:{
            grnState,
            grnId
        }
    });
    return result.data;
}


export async function updateStockAndStoragerack(grnState,grnId,dpId,stock) {
    let result = await axios.post('http://localhost:8181/backEndWarehouse/goodsreceivednote/updateStockAndStoragerack',stock,{
        params:{
            grnState,
            grnId,
            dpId
        }
    })
    return result.data;
}

export async function updateStock(grnState,grnId,dpId,stock) {
    let result = await axios.post('http://localhost:8181/backEndWarehouse/goodsreceivednote/updateStock',stock,{
        params:{
            grnState,
            grnId,
            dpId
        }
    })
    return result.data;
}


export async function getGrnBySplId(grnSplId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/goodsreceivednote/getGrnBySplId',{
        params:{
            grnSplId,
        }
    })
    return result.data;
}