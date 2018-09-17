package com.mesozi.app.buidafrique.Utils;

import com.mesozi.app.buidafrique.Models.ProductCategory;
import com.raizlabs.android.dbflow.sql.language.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ekirapa on 9/4/18 .
 */
public class DatabaseInit {
    public static void buildCategoriesDB() {
        Delete.table(ProductCategory.class);
        String ip = "https://buildafrique.com/our-solutions/";
        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(new ProductCategory("Project Finance & Capital Structuring", String.format(Locale.getDefault(), "%sfinance-management/project-finance-capital-structuring/", ip), 1));
        productCategories.add(new ProductCategory("Project Management", String.format(Locale.getDefault(), "%sfinance-management/project-management/", ip), 1));
        productCategories.add(new ProductCategory("Feasibility Studies & Market Strategy Planning", String.format(Locale.getDefault(), "%sfinance-management/market-strategic-planning-feasibility-research/", ip), 1));
        productCategories.add(new ProductCategory("Investment Design Appraisal", String.format(Locale.getDefault(), "%sfinance-management/option-design-appraisal/", ip), 1));

        productCategories.add(new ProductCategory("Quantity Surveying", String.format(Locale.getDefault(), "%squantity-surveying/quantity-surveying/", ip), 2));
        productCategories.add(new ProductCategory("Development Cost Management", String.format(Locale.getDefault(), "%sfinance-management/project-management/", ip), 2));
        productCategories.add(new ProductCategory("Construction Costs Consultancy", String.format(Locale.getDefault(), "%squantity-surveying/89-2/", ip), 2));
        productCategories.add(new ProductCategory("Construction Contract Administration", String.format(Locale.getDefault(), "%squantity-surveying/construction-cost-advisory/", ip), 2));


        productCategories.add(new ProductCategory("Physical Planning & Planning Permissions", String.format(Locale.getDefault(), "%sland-environment/physical-planning-planning-permissions/", ip), 3));
        productCategories.add(new ProductCategory("Land Surveying", String.format(Locale.getDefault(), "%sland-environment/land-surveying/", ip), 3));
        productCategories.add(new ProductCategory("Environmental Management & Impact Assessment", String.format(Locale.getDefault(), "%sland-environment/environmental-management-impact-assessment/", ip), 3));
        productCategories.add(new ProductCategory("Occupation Health & Safety Management", String.format(Locale.getDefault(), "%sland-environment/occupation-health-safety-management/", ip), 3));

        productCategories.add(new ProductCategory("Real Estate Investments & Structured Solutions", String.format(Locale.getDefault(), "%sproperty-investments/113-2/", ip), 4));
        productCategories.add(new ProductCategory("Real Estate & Property Valuation", String.format(Locale.getDefault(), "%sproperty-investments/property-valuation/", ip), 4));
        productCategories.add(new ProductCategory("Property Management", String.format(Locale.getDefault(), "%sproperty-investments/property-management/", ip), 4));
        productCategories.add(new ProductCategory("Facility Management", String.format(Locale.getDefault(), "%sproperty-investments/facility-management//", ip), 4));

        for (ProductCategory productCategory : productCategories) {
            productCategory.save();
        }
    }
}
