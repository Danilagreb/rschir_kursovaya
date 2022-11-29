const toastBtn = document.querySelector(".toast-close")
const formCreateOrder = document.querySelector("#create-order-form")
const orderPrice = document.querySelector("#order-price")
const orderPriceDelivery = document.querySelector("#order-price-delivery")
const buttonsAddOrder = document.querySelectorAll(".button-add-order")
const addedGoods = document.querySelector("#collection-added-goods")
const filterOrderForm = document.querySelector("#filter-order-form")
const checkboxWithDelivery = document.querySelector("#withDelivery")
window.printInputs = () => [...formCreateOrder.querySelectorAll("input")]
if(toastBtn) {
    toastBtn.addEventListener("click", (e) => {
        e?.target?.closest?.(".toast")?.remove?.()
    })
}

if(formCreateOrder) {
    const updatePrice = () => {
        const addedItemsToOrder = [...addedGoods.querySelectorAll(".collection-item")]
        const summaryPrice = addedItemsToOrder.reduce((acc, item) => {
            return acc + Number(item.dataset.price) * Number(item.dataset.count);
        }, 0)
        console.log({addedItemsToOrder, summaryPrice})
        orderPrice.textContent = `Цена заказа - ${summaryPrice}`
    }
    updatePrice()
    buttonsAddOrder.forEach(btn => {
        btn.addEventListener("click", (event) => {
            const target = event.target
            const id = target.dataset.id
            const colItem = formCreateOrder.querySelector(`.collection-item[data-id='${id}']`)

            const inputs = formCreateOrder.querySelectorAll(".good-item");
            const countCurrentItem = [...inputs].filter(item => item.dataset.id === target.dataset.id).length
            const index = !inputs.length
                ? 0
                : Number(inputs[inputs.length - 1].dataset.index) + 1
            const addedInputHtml = `<input type="text" data-id="${target.dataset.id}" data-index="${index}" hidden class="validate good-item" name="goodsIds[]" field="goodsIds[]" value="${target.dataset.id}">`
            const addedListHtml = `
                <li class="collection-item" data-index="${index}" data-count="${countCurrentItem + 1}" data-price="${target.dataset.price}" data-id="${target.dataset.id}" style="display: flex; flex-direction: column">
                    <div>${target.dataset.name}</div>
                    <div>Цена - ${target.dataset.price}</div>
                    <div class="count">Количество - ${countCurrentItem + 1}</div>
                    <button type="button" class="m-x-auto w100 remove-goods waves-effect waves-light mini-btn btn-small">Удалить</button>
                </li>
            `
            if(colItem) {
                colItem.outerHTML = addedListHtml
            } else {
                addedGoods.insertAdjacentHTML("beforeend", addedListHtml)
            }
            formCreateOrder.insertAdjacentHTML("beforeend", addedInputHtml)
            updatePrice()
        })
    })
    addedGoods.addEventListener("click", (event) => {
        const colItem = event.target.closest(".collection-item")
        const countEl = colItem.querySelector(".count")
        if(!colItem) {
            return
        }
        if(event.target.closest(".remove-goods")) {
            const id = colItem.dataset.id
            formCreateOrder.querySelectorAll(`.collection-item[data-id='${id}']`).forEach(item => {
                item.remove()
            })
            formCreateOrder.querySelectorAll(`.good-item[data-id='${id}']`).forEach(item => {
                item.remove()
            })
        }
        if(event.target.closest(".add-good")) {
            const id = colItem.dataset.id
            const inputs = formCreateOrder.querySelectorAll(".good-item");
            const index = !inputs.length
                ? 0
                : Number(inputs[inputs.length - 1].dataset.index) + 1
            const addedInputHtml = `<input type="text" data-id="${id}" data-index="${index}" hidden class="validate good-item" name="goodsIds[]" field="goodsIds[]" value="${id}">`
            const newCount = +colItem.dataset.count + 1
            countEl.textContent = `Количество - ${newCount}`
            colItem.dataset.count = `${newCount}`
            formCreateOrder.insertAdjacentHTML("beforeend", addedInputHtml)
        }
        if(event.target.closest(".minus-good")) {
            const id = colItem.dataset.id
            const inputs = formCreateOrder.querySelectorAll(".good-item");
            const newCount = +colItem.dataset.count - 1
            countEl.textContent = `Количество - ${newCount}`
            colItem.dataset.count = `${newCount}`
            const inputForDel = [...inputs].find(item => item.dataset.id === id)
            inputForDel?.remove();
            if(newCount <= 0) {
                formCreateOrder.querySelectorAll(`.collection-item[data-id='${id}']`).forEach(item => {
                    item.remove()
                })
                formCreateOrder.querySelectorAll(`.good-item[data-id='${id}']`).forEach(item => {
                    item.remove()
                })
            }
        }
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