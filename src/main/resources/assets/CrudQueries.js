
$(function () {
  load();
  initModal();
});
function create(name, password) {
  $.post("/api/users", JSON.stringify({name: name, password: password}), function () {
    load();
  }, "json");
}
function remove(id) {
  $.ajax({
    method: "DELETE",
    url: "/api/users/" + id
  }).done(function () {
    load();
  });
}
function update(id, name, password) {
  $.ajax({
    method: "PUT",
    url: "/api/users/" + id,
    data: JSON.stringify({name: name, password: password})
  }).done(function () {
    load();
  });
}
function load() {
  $("#content").children().remove();
  $.getJSON("/api/users", function (data) {
    $.each(data, function (key, val) {
      $("<tr><td>" + val.id + "</td><td>" + val.name + "</td><td>" + val.password + "</td>" +
        "<td>" +
        "<button data-action='edit' class='btn btn-primary btn-sm product-edit' " +
        "data-toggle='modal' " +
        "data-target='#productModal' " +
        "data-name='" + val.name + "' " +
        "data-email='" + val.password + "' " +
        "data-id='" + val.id + "'>" +
        "<span class='glyphicon glyphicon-pencil'></span>" +
        "</button>" +
        "&nbsp;" +
        "<button class='btn btn-danger btn-sm product-delete' data-id='" + val.id + "'>" +
        "   <span class='glyphicon glyphicon-minus'></span>" +
        "</button>" +
        "</td>" +
        "</tr>").appendTo("#content");
    });
    initCallbacks();
  });
}
function initCallbacks() {
  $(".product-delete").unbind().click(function() {
    var id = $(this).data("id");
    remove(id);
  });
}
function initModal() {
  $("#productModal").on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var action = button.data('action');
    var id = button.data('id');
    var productAction = $("#productAction");
    productAction.unbind();
    var modal = $(this);
    if (action === "add") {
      modal.find('.modal-title').text("Add a bottle");
      modal.find('#product-name').val("");
      modal.find('#product-email').val("");
      productAction.click(function () {
        create($("#product-name").val(), $("#product-email").val());
        $('#productModal').modal('toggle');
      });
    } else {
      modal.find('.modal-title').text("Edit a bottle");
      modal.find('#product-name').val(button.data("name"));
      modal.find('#product-email').val(button.data("password"));
      productAction.click(function () {
        update(id, $("#product-name").val(), $("#product-email").val());
        $('#productModal').modal('toggle');
      });
    }
  })
}