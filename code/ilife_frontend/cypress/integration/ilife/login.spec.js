describe("LoginPage test", function () {
  it("login to weibo", function () {
    cy.visit("http://49.234.125.131/login");
    cy.get("input#nameinput").type("ilife0905");
    cy.get("input#psdinput").type("123456");
    cy.get("div.icheckbox_square-blue").click();
    cy.get("p#login").click();

    cy.wait(500);
    cy.url().should("include", "home/weibo");
  });
});
