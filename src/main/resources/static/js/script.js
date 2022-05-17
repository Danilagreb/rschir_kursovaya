const toastBtn = document.querySelector(".toast-close")
const formCreateOrder = document.querySelector("#create-order-form")
const orderPrice = document.querySelector("#order-price")
const orderPriceDelivery = document.querySelector("#order-price-delivery")
const buttonsAddOrder = document.querySelectorAll(".button-add-order")
const addedGoods = document.querySelector("#collection-added-goods")
const filterOrderForm = document.querySelector("#filter-order-form")
const checkboxWithDelivery = document.querySelector("#withDelivery")

if(toastBtn) {
    toastBtn.addEventListener("click", (e) => {
        e?.target?.closest?.(".toast")?.remove?.()
    })
}

if(formCreateOrder) {
    const updatePrice = () => {
        const addedItemsToOrder = [...addedGoods.querySelectorAll(".collection-item")]
        const summaryPrice = addedItemsToOrder.reduce((acc, item) => {
            return acc + Number(item.dataset.price);
        }, 0)
        console.log({addedItemsToOrder, summaryPrice})
        orderPrice.textContent = `Цена заказа - ${summaryPrice}`
    }
    updatePrice()
    buttonsAddOrder.forEach(btn => {
        btn.addEventListener("click", (event) => {
            const target = event.target
            const inputs = formCreateOrder.querySelectorAll(".good-item");
            const index = !inputs.length
                ? 0
                : Number(inputs[inputs.length - 1].dataset.index) + 1
            const addedInputHtml = `<input type="text" data-index="${index}" hidden class="validate good-item" name="goodsIds[]" field="goodsIds[]" value="${target.dataset.id}">`
            const addedListHtml = `
                <li class="collection-item" data-index="${index}" data-price="${target.dataset.price}" data-id="${target.dataset.id}" style="display: flex; flex-direction: column">
                    <div>${target.dataset.name}</div>
                    <div>Цена - ${target.dataset.price}</div>
                    <button class="m-x-auto w100 remove-goods waves-effect waves-light mini-btn btn-small">Удалить</button>
                </li>
            `
            formCreateOrder.insertAdjacentHTML("beforeend", addedInputHtml)
            addedGoods.insertAdjacentHTML("beforeend", addedListHtml)
            updatePrice()
        })
    })
    addedGoods.addEventListener("click", (event) => {
        const colItem = event.target.closest(".collection-item")
        const removedIndex = colItem?.dataset.index
        const input = formCreateOrder.querySelector(`.good-item[data-index='${removedIndex}']`)
        input.remove();
        colItem.remove();
        updatePrice()
    })
}
if(filterOrderForm) {
    filterOrderForm.addEventListener("submit", (e) => {
        const inputs = formCreateOrder.querySelectorAll("input")
        for(let input of inputs) {
            input.style.display = "none"
            filterOrderForm.insertAdjacentElement("beforeend", input)
        }
    })
}

if(checkboxWithDelivery) {
    checkboxWithDelivery.addEventListener("change", (event) => {
        if(event.target.checked) {
            orderPriceDelivery.style.display = "block"
        } else {
            orderPriceDelivery.style.display = "none"
        }
    })
}